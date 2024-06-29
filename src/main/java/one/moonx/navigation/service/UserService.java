package one.moonx.navigation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import one.moonx.navigation.pojo.dto.UserLoginDTO;
import one.moonx.navigation.pojo.entity.User;
import one.moonx.navigation.pojo.vo.UserLoginVO;

public interface UserService extends IService<User> {
    /**
     * 创建用户
     *
     * @param user 用户
     */
    public void createUser(User user);

    /**
     * 更新用户
     *
     * @param user 用户
     */
    public void updateUser(User user);


    /**
     * 登录
     *
     * @param userLoginDTO 用户登录 DTO
     * @return {@link UserLoginVO }
     */
    public UserLoginVO login(UserLoginDTO userLoginDTO);
}