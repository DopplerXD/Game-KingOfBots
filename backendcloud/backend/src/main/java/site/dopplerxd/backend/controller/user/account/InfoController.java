package site.dopplerxd.backend.controller.user.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import site.dopplerxd.backend.services.user.account.InfoService;

import java.util.Map;

@RestController
public class InfoController {

    @Autowired
    private InfoService infoService;

    @GetMapping("/user/account/info")
    public Map<String, String> getInfo() {
        return infoService.getInfo();
    }
}
