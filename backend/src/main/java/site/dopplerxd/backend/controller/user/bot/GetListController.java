package site.dopplerxd.backend.controller.user.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import site.dopplerxd.backend.entity.Bot;
import site.dopplerxd.backend.services.user.bot.GetListService;

import java.util.List;

@RestController
public class GetListController {

    @Autowired
    private GetListService getListService;

    @GetMapping("/user/bot/list")
    public List<Bot> getList() {
        return getListService.getList();
    }
}
