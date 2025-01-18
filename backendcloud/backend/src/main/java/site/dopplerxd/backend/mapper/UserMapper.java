package site.dopplerxd.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import site.dopplerxd.backend.pojo.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
