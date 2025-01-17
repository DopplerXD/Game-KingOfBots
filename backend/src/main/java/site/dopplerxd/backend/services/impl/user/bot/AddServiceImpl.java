package site.dopplerxd.backend.services.impl.user.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.dopplerxd.backend.pojo.Bot;
import site.dopplerxd.backend.pojo.User;
import site.dopplerxd.backend.mapper.BotMapper;
import site.dopplerxd.backend.services.user.bot.AddService;
import site.dopplerxd.backend.utils.GetUserUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AddServiceImpl implements AddService {

    @Autowired
    private BotMapper botMapper;

    @Override
    public Map<String, String> add(Map<String, String> data) {
        User user = GetUserUtil.getUser();

        String title = data.get("title");
        String description = data.get("description");
        String content = data.get("content");

        Map<String, String> map = new HashMap<>();

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
        if (description == null || description.length() == 0) {
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

        Date now = new Date();
        Bot bot = new Bot(null, user.getId(), title, description, content, 1500, now, now);
        botMapper.insert(bot);

        map.put("error_message", "success");

        return map;
    }
}
