package site.dopplerxd.backend.consumer;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Controller;
import site.dopplerxd.backend.entity.User;
import site.dopplerxd.backend.mapper.UserMapper;
import site.dopplerxd.backend.utils.Game;
import site.dopplerxd.backend.utils.JwtAuthentication;

import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint("/websocket/{token}")  // 注意不要以'/'结尾
public class WebSocketServer {

    private Session session = null;
    private User user;
    private static ConcurrentHashMap<Integer, WebSocketServer> users = new ConcurrentHashMap<>(); // 线程安全的Map
    private static CopyOnWriteArraySet<User> matchpool = new CopyOnWriteArraySet<>(); // 线程安全的Set

    private static UserMapper userMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        WebSocketServer.userMapper = userMapper;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) throws IOException {
        // 建立连接
        this.session = session;
        System.out.println("WebSocket opened");
        Integer userId = JwtAuthentication.getUserId(token);
        this.user = userMapper.selectById(userId);

        if (this.user != null) {
            users.put(userId, this);
        } else {
            this.session.close();
        }

    }

    @OnClose
    public void onClose() {
        // 关闭链接
        System.out.println("WebSocket closed");
        if (this.user != null) {
            users.remove(this.user.getId());
            matchpool.remove(this.user);
        }
    }

    private void startMatching() {
        System.out.println("start matching");
        matchpool.add(this.user);

        while (matchpool.size() >= 2) {
            Iterator<User> iterator = matchpool.iterator();
            User user1 = iterator.next();
            User user2 = iterator.next();
            matchpool.remove(user1);
            matchpool.remove(user2);

            Game game = new Game(13, 16, 20);
            game.createGameMap();

            JSONObject res1 = new JSONObject();
            res1.put("event", "start-matching");
            res1.put("opponent_username", user2.getUsername());
            res1.put("opponent_photo", user2.getPhoto());
            res1.put("gamemap", game.getGameMap());
            users.get(user1.getId()).sendMessage(res1.toJSONString());

            JSONObject res2 = new JSONObject();
            res2.put("event", "start-matching");
            res2.put("opponent_username", user1.getUsername());
            res2.put("opponent_photo", user1.getPhoto());
            res2.put("gamemap", game.getGameMap());
            users.get(user2.getId()).sendMessage(res2.toJSONString());
        }
    }

    private void stopMatching() {
        System.out.println("stop matching");
        matchpool.remove(this.user);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        // 从Client接收消息
        System.out.println("Received message");
        JSONObject data = JSONObject.parseObject(message);
        String event = data.getString("event");
        if ("start-matching".equals(event)) {
            // 开始匹配
            startMatching();
        } else if ("stop-matching".equals(event)) {
            // 取消匹配
            stopMatching();
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    public void sendMessage(String message) {
        // 向Client发送消息
        // this.session.getAsyncRemote().sendText(message);

        // 异步锁
        synchronized (this.session) {
            try {
                this.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}