package site.dopplerxd.backend.controller.user;

import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.dopplerxd.backend.entity.User;
import site.dopplerxd.backend.mapper.UserMapper;
import site.dopplerxd.backend.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/find/all")
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/find/{id}")
    public User findById(@PathVariable("id") int id) {
        return userService.getById(id);
    }

    @GetMapping("/add/{id}/{username}/{password}")
    public String updateUser(@PathVariable("id") int id, @PathVariable("username") String username, @PathVariable("password") String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userService.saveOrUpdate(new User(id, username, passwordEncoder.encode(password)));
        return "update success";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.removeById(id);
        return "delete success";
    }
}
