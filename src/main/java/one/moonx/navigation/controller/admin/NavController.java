package one.moonx.navigation.controller.admin;

import one.moonx.navigation.base.Result;
import one.moonx.navigation.constant.MessageConstant;
import one.moonx.navigation.convert.NavConvert;
import one.moonx.navigation.pojo.dto.NavDTO;
import one.moonx.navigation.pojo.vo.NavVO;
import one.moonx.navigation.service.NavService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/nav")
public class NavController {
    @Autowired
    private NavService navService;
    @Autowired
    private NavConvert navConvert;

    /**
     * 获取导航
     *
     * @return {@link Result }<{@link List }<{@link NavVO }>>
     */
    @GetMapping
    public Result<List<NavVO>> getNav() {
        List<NavVO> navVOList = navConvert.convertVO(navService.list());
        return Result.success.msgAndData(MessageConstant.GET_SUCCESS, navVOList);
    }

    /**
     * 按 ID 获取导航
     *
     * @param id id
     * @return {@link Result }<{@link NavVO }>
     */
    @GetMapping("/{id}")
    public Result<NavVO> getNavById(@PathVariable Integer id) {
        NavVO navVO = navConvert.convertVO(navService.getById(id));
        return Result.success.msgAndData(MessageConstant.GET_SUCCESS, navVO);
    }

    /**
     * 创建导航
     *
     * @param navDTO 导航 DTO
     * @return {@link Result }
     */
    @PostMapping
    public Result<String> createNav(@RequestBody NavDTO navDTO) {
        navService.createNav(navDTO);
        return Result.success.msg(MessageConstant.ADD_SUCCESS);
    }

    /**
     * 更新导航
     *
     * @param navDTO 导航 DTO
     * @return {@link Result }
     */
    @PutMapping
    public Result<String> updateNav(@RequestBody NavDTO navDTO) {
        navService.updateNav(navDTO);
        return Result.success.msg(MessageConstant.UPDATE_SUCCESS);
    }

    /**
     * 删除导航
     *
     * @param id id
     * @return {@link Result }
     */
    @DeleteMapping("/{id}")
    public Result<String> deleteNav(@PathVariable Integer id) {
        navService.removeById(id);
        return Result.success.msg(MessageConstant.DELETE_SUCCESS);
    }
}
