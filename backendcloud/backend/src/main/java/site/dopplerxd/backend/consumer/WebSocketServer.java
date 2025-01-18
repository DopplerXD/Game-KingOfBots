package site.dopplerxd.backend.consumer;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import site.dopplerxd.backend.mapper.RecordMapper;
import site.dopplerxd.backend.pojo.User;
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
    public static ConcurrentHashMap<Integer, WebSocketServer> users = new ConcurrentHashMap<>(); // 线程安全的Map
    private static UserMapper userMapper;
    public static RecordMapper recordMapper;
    private static RestTemplate restTemplate;
    private Game game = null;
    final private static String addPlayerUrl = "http://127.0.0.1:3001/player/add";
    final private static String removePlayerUrl = "http://127.0.0.1:3001/player/remove";

    @Autowired
    private void setUserMapper(UserMapper userMapper) {
        WebSocketServer.userMapper = userMapper;
    }
    @Autowired
    public void setUserMapper(RecordMapper recordMapperMapper) {
        WebSocketServer.recordMapper = recordMapperMapper;
    }
    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        WebSocketServer.restTemplate = restTemplate;
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
        }
    }

    private void startGame(Integer id1, Integer id2) {
        User user1 = userMapper.selectById(id1);
        User user2 = userMapper.selectById(id2);
        Game game = new Game(13, 16, 20, user1.getId(), user2.getId());
        game.createGameMap();
        users.get(user1.getId()).game = game;
        users.get(user2.getId()).game = game;

        game.start();

        JSONObject resGame = new JSONObject();
        resGame.put("id1", game.getPlayer1().getId());
        resGame.put("sx1", game.getPlayer1().getSx());
        resGame.put("sy1", game.getPlayer1().getSy());
        resGame.put("id2", game.getPlayer2().getId());
        resGame.put("sx2", game.getPlayer2().getSx());
        resGame.put("sy2", game.getPlayer2().getSy());
        resGame.put("map", game.getGameMap());

        JSONObject res1 = new JSONObject();
        res1.put("event", "start-matching");
        res1.put("opponent_username", user2.getUsername());
        res1.put("opponent_photo", user2.getPhoto());
        res1.put("game", resGame);
        users.get(user1.getId()).sendMessage(res1.toJSONString());

        JSONObject res2 = new JSONObject();
        res2.put("event", "start-matching");
        res2.put("opponent_username", user1.getUsername());
        res2.put("opponent_photo", user1.getPhoto());
        res2.put("game", resGame);
        users.get(user2.getId()).sendMessage(res2.toJSONString());
    }

    private void startMatching() {
        System.out.println("start matching");
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("user_id", this.user.getId().toString());
        data.add("rating", this.user.getRating().toString());
        restTemplate.postForObject(addPlayerUrl, data, String.class);
    }

    private void stopMatching() {
        System.out.println("stop matching");
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("user_id", this.user.getId().toString());
        restTemplate.postForObject(removePlayerUrl, data, String.class);
    }

    private void move(int direction) {
        if (game.getPlayer1().getId().equals(user.getId())) {
            game.setNextStep1(direction);
        } else if (game.getPlayer2().getId().equals(user.getId())) {
            game.setNextStep2(direction);
        }
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
        } else if ("move".equals(event)) {
            // 移动
            move(data.getInteger("direction"));
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