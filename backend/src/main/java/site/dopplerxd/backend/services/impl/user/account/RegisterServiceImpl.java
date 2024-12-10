package site.dopplerxd.backend.services.impl.user.account;

import org.springframework.stereotype.Service;
import site.dopplerxd.backend.services.user.account.RegisterService;

import java.util.HashMap;
import java.util.Map;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Override
    public Map<String, String> register(String username, String password, String confirmedPassword) {
        Map<String, String> map = new HashMap<>();
        if (username == null) {
            map.put("error_message", "用户名不能为空");
            return map;
        }
        if (password == null) {
            map.put("error_message", "密码不能为空");
            return map;
        }
        if (confirmedPassword == null) {
            map.put("error_message", "确认密码不能为空");
            return map;
        }

        username = username.trim();
        if (username.length() == 0) {
            map.put("error_message", "用户名不能为空");
            return map;
        }
        if (username.length() > 100) {
            map.put("error_message", "用户名长度不能超过100");
            return map;
        }

        if (password.length() > 100 || confirmedPassword.length() > 100) {
            map.put("error_message", "密码长度不能超过100");
            return map;
        }

        map.put("error_message", "注册成功");
        map.put("username", username);
        map.put("password", password);

        return map;
    }
}
