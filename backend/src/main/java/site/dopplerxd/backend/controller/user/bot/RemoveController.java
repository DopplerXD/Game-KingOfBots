package site.dopplerxd.backend.controller.user.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.dopplerxd.backend.services.user.bot.RemoveService;

import java.util.Map;

@RestController
public class RemoveController {

    @Autowired
    private RemoveService removeService;

    @PostMapping ("/user/bot/remove")
    public Map<String, String> remove(@RequestParam Map<String, String> data) {
        return removeService.remove(data);
    }
}
