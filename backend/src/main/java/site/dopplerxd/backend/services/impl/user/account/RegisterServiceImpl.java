package site.dopplerxd.backend.services.impl.user.account;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import site.dopplerxd.backend.pojo.User;
import site.dopplerxd.backend.services.UserService;
import site.dopplerxd.backend.services.impl.utils.UserDetailsImpl;
import site.dopplerxd.backend.services.user.account.RegisterService;
import site.dopplerxd.backend.utils.JwtUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

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

        if (password.length() == 0) {
            map.put("error_message", "密码不能为空");
            return map;
        }
        if (confirmedPassword.length() == 0) {
            map.put("error_message", "确认密码不能为空");
            return map;
        }
        if (password.length() > 100) {
            map.put("error_message", "密码长度不能超过100");
            return map;
        }
        if (confirmedPassword.length() > 100) {
            map.put("error_message", "确认密码长度不能超过100");
            return map;
        }

        if (!password.equals(confirmedPassword)) {
            map.put("error_message", "两次密码输入不一致");
            return map;
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        List<User> users = userService.list(queryWrapper);
        if (users.size() > 0) {
            map.put("error_message", "用户名已存在");
            return map;
        }


        String encodedPassword = passwordEncoder.encode(password);
        String photo = "https://cdn.acwing.com/media/user/profile/photo/450178_lg_963ea242b1.jpg";
        User user = new User(null, username, encodedPassword, photo);
        userService.save(user);
        map.put("error_message", "success");

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

        Authentication authenticate = authenticationManager.authenticate(token); // 登录失败会自动处理异常

        UserDetailsImpl loginUser = (UserDetailsImpl) authenticate.getPrincipal();
        user = loginUser.getUser();
        String jwtToken = JwtUtil.createJWT(user.getId().toString());
        map.put("token", jwtToken);

        return map;
    }
}
