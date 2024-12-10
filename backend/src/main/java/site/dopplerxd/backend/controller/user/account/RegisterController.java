package site.dopplerxd.backend.controller.user.account;

import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import site.dopplerxd.backend.services.user.account.RegisterService;

import java.util.Map;

@RestController
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @PutMapping("/user/account/register")
    public Map<String, String> register() {
        return registerService.register();
    }
}
