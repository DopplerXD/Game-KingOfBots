package site.dopplerxd.backend.services;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.dopplerxd.backend.pojo.User;
import site.dopplerxd.backend.mapper.UserMapper;

import java.util.List;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {

    @Autowired
    private UserMapper userMapper;

    public List<User> findAll() {
        return userMapper.selectList(null);
    }
}
