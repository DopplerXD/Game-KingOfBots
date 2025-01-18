package site.dopplerxd.backend.services.impl.user.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.dopplerxd.backend.pojo.Bot;
import site.dopplerxd.backend.pojo.User;
import site.dopplerxd.backend.mapper.BotMapper;
import site.dopplerxd.backend.services.user.bot.UpdateService;
import site.dopplerxd.backend.utils.GetUserUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UpdateServiceImpl implements UpdateService {

    @Autowired
    private BotMapper botMapper;

    @Override
    public Map<String, String> update(Map<String, String> data) {
        User user = GetUserUtil.getUser();

        Map<String, String> map =  new HashMap<>();

        Integer botId = Integer.parseInt(data.get("bot_id"));
        Bot bot = botMapper.selectById(botId);

        String title = data.get("title");
        String description = data.get("description");
        String content = data.get("content");

        if (bot == null) {
            map.put("error_message", "bot不存在或已被删除");
            return map;
        }

        if (!bot.getUserId().equals(user.getId())) {
            map.put("error_message", "你没有权限修改该bot");
            return map;
        }

        if (title == null || title.length() == 0) {
            map.put("error_message", "标题不能为空");
            return map;
        }
        if (content == null || content.length() == 0) {
            map.put("error_message", "代码不能为空");
            return map;
        }
        if (title.length() > 100) {
            map.put("error_message", "标题长度不能超过100");
            return map;
        }
        if (description == null) {
            description = "这个用户很懒，什么也没有写。";
        }
        if (description.length() > 300) {
            map.put("error_message", "bot的描述长度不能超过300");
            return map;
        }
        if (content.length() > 10000) {
            map.put("error_message", "代码长度不能超过10000");
            return map;
        }

        Date date = new Date();
        Bot newBot = new Bot(Integer.parseInt(data.get("bot_id")),
                user.getId(),
                title,
                description,
                content,
                bot.getCreateTime(),
                date);
        botMapper.updateById(newBot);

        map.put("error_message", "success");

        return map;
    }
}
