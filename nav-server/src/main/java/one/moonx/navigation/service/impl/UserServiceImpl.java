package one.moonx.navigation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import one.moonx.navigation.constant.JwtClaimsConstant;
import one.moonx.navigation.constant.MessageConstant;
import one.moonx.navigation.constant.UserConstant;
import one.moonx.navigation.exception.BaseException;
import one.moonx.navigation.mapper.UserMapper;
import one.moonx.navigation.pojo.dto.UserLoginDTO;
import one.moonx.navigation.pojo.entity.User;
import one.moonx.navigation.pojo.vo.UserLoginVO;
import one.moonx.navigation.properties.JwtProperties;
import one.moonx.navigation.service.UserService;
import one.moonx.navigation.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 检查用户名和密码
     *
     * @param username 用户名
     * @param password 密码
     */
    private void checkUserNameAndPassword(String username, String password) {
        //判断用户名是否合法
        if (username != null) {
            if (username.isEmpty() || !(username.length() <= 24 && username.length() >= 6)) {
                throw new BaseException(UserConstant.USER_USERNAME_UNLAWFUL);
            }
        }

        //判断密码是否合法
        if (password != null) {
            if (password.isEmpty() || !(password.length() <= 24 && password.length() >= 6)) {
                throw new BaseException(UserConstant.USER_PASSWORD_UNLAWFUL);
            }
        }
    }

    @Override
    public User getById(Serializable id) {
        User user = super.getById(id);
        if (user == null) {
            throw new BaseException(MessageConstant.ID_ERROR);
        }
        return user;
    }

    /**
     * 创建用户
     *
     * @param user 用户
     */
    @Override
    public void createUser(User user) {
        //清空提交来的id
        user.setId(null);

        //检查用户名和密码
        checkUserNameAndPassword(user.getUsername(), user.getPassword());

        //密码MD5加密
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));

        //保存
        save(user);
    }

    /**
     * 更新用户
     *
     * @param user 用户
     */
    @Override
    public void updateUser(User user) {
        //检查用户名和密码
        checkUserNameAndPassword(user.getUsername(), user.getPassword());

        //获取数据库用户
        User dbUser = getById(user.getId());

        //判断有没有这个用户
        if (dbUser == null) {
            throw new BaseException(UserConstant.NOT_REGISTRATION);
        }

        //判断有没有修改
        if (dbUser.getUsername().equals(user.getUsername()) && dbUser.getPassword().equals(user.getPassword())) {
            throw new BaseException(UserConstant.UNMODIFIED_DATA);
        }

        //MD5加密
        if (user.getPassword() != null) {
            user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        }

        updateById(user);
    }

    /**
     * 登录
     *
     * @param userLoginDTO 用户登录 DTO
     * @return {@link UserLoginVO }
     */
    @Override
    public UserLoginVO login(UserLoginDTO userLoginDTO) {
        //获取数据库用户
        User dbUser = lambdaQuery().eq(User::getUsername, userLoginDTO.getUsername()).one();

        //判断有没有用户
        if (dbUser == null) {
            throw new BaseException(UserConstant.NOT_REGISTRATION);
        }

        //判断密码
        String password = DigestUtils.md5DigestAsHex(userLoginDTO.getPassword().getBytes());
        if (!password.equals(dbUser.getPassword())) {
            throw new BaseException(UserConstant.USERNAME_OR_PASSWORD_ERROR);
        }

        //生成token
        Map<String, Object> payload = new HashMap<>();
        payload.put(JwtClaimsConstant.USER_ID, dbUser.getId());
        payload.put(JwtClaimsConstant.USER_NAME, dbUser.getUsername());
        String token = JwtUtil.createJWT(jwtProperties.getAdminSecretKey(), jwtProperties.getAdminTtl(), payload);


        return UserLoginVO.builder()
                .id(dbUser.getId())
                .username(dbUser.getUsername())
                .token(token).build();
    }

    /**
     * 按 ID 删除
     *
     * @param id id
     * @return boolean
     */
    @Override
    public boolean removeById(Serializable id) {
        boolean result = super.removeById(id);
        if (!result) {
            throw new BaseException(MessageConstant.ID_ERROR);
        }
        return true;
    }
}