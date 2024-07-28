package one.moonx.navigation.controller.admin;

import one.moonx.navigation.base.Result;
import one.moonx.navigation.constant.UserConstant;
import one.moonx.navigation.convert.UserConvert;
import one.moonx.navigation.pojo.dto.UserDTO;
import one.moonx.navigation.pojo.dto.UserLoginDTO;
import one.moonx.navigation.pojo.vo.UserLoginVO;
import one.moonx.navigation.pojo.vo.UserVO;
import one.moonx.navigation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("adminUserController")
@RequestMapping("/admin/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserConvert userConvert;

    /**
     * 获取用户
     *
     * @return {@link Result }<{@link List }<{@link UserVO }>>
     */
    @GetMapping
    public Result<List<UserVO>> getUser() {
        return Result.success.msgAndData(UserConstant.USER_GET_SUCCESS, userConvert.convertVO(userService.list()));
    }

    /**
     * 按 ID 获取用
     *
     * @param id id
     * @return {@link Result }<{@link UserDTO }>
     */
    @GetMapping("/{id}")
    public Result<UserVO> getUserById(@PathVariable Integer id) {
        return Result.success.msgAndData(UserConstant.USER_GET_SUCCESS, userConvert.convert(userService.getById(id)));
    }

    /**
     * 创建用户
     *
     * @param userDTO 用户 DTO
     * @return {@link Result }
     */
    @PostMapping
    public Result<String> createUser(@RequestBody UserDTO userDTO) {
        userService.createUser(userConvert.convert(userDTO));
        return Result.success.msg(UserConstant.USER_CREATE_SUCCESS);
    }

    /**
     * 更新用户
     *
     * @param userDTO 用户 DTO
     * @return {@link Result }
     */
    @PutMapping
    public Result<String> updateUser(@RequestBody UserDTO userDTO) {
        userService.updateUser(userConvert.convert(userDTO));
        return Result.success.msg(UserConstant.USER_UPDATE_SUCCESS);
    }

    /**
     * 删除用户
     *
     * @param id id
     * @return {@link Result }
     */
    @DeleteMapping("/{id}")
    public Result<String> deleteUser(@PathVariable Integer id) {
        userService.removeById(id);
        return Result.success.msg(UserConstant.USER_DELETE_SUCCESS);
    }

    /**
     * 登录
     *
     * @param userLoginDTO 用户登录 DTO
     * @return {@link Result }<{@link UserLoginDTO }>
     */
    @GetMapping("/login")
    public Result<UserLoginVO> login(UserLoginDTO userLoginDTO) {
        UserLoginVO userLoginVO = userService.login(userLoginDTO);
        return Result.success.msgAndData(UserConstant.USER_LOGIN_SUCCESS, userLoginVO);
    }
}