package site.dopplerxd.backend.services.impl.user.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.dopplerxd.backend.entity.Bot;
import site.dopplerxd.backend.mapper.BotMapper;
import site.dopplerxd.backend.services.user.bot.GetListService;

import java.util.List;

@Service
public class GetListServiceImpl implements GetListService {

    @Autowired
    private BotMapper botMapper;

    @Override
    public List<Bot> getList() {
        return botMapper.selectList(null);
    }
}
