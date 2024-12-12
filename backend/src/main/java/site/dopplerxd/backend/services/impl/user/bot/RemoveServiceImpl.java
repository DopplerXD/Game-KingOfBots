package site.dopplerxd.backend.services.impl.user.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.dopplerxd.backend.entity.Bot;
import site.dopplerxd.backend.entity.User;
import site.dopplerxd.backend.mapper.BotMapper;
import site.dopplerxd.backend.services.user.bot.RemoveService;
import site.dopplerxd.backend.utils.GetUserUtil;

import java.util.HashMap;
import java.util.Map;

@Service
public class RemoveServiceImpl implements RemoveService {

    @Autowired
    private BotMapper botMapper;

    @Override
    public Map<String, String> remove(Map<String, String> data) {
        User user = GetUserUtil.getUser();

        Map<String, String> map = new HashMap<>();
        Integer botId = Integer.parseInt(data.get("bot_id"));
        Bot bot = botMapper.selectById(botId);

        if (bot == null) {
            map.put("error_message", "bot不存在或已被删除");
            return map;
        }

        if (!bot.getId().equals(user.getId())) {
            map.put("error_message", "你没有权限删除该bot");
            return map;
        }

        map.put("error_message", "success");
        return map;
    }
}
