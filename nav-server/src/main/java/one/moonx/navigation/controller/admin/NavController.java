package one.moonx.navigation.controller.admin;

import one.moonx.navigation.base.Result;
import one.moonx.navigation.base.ResultPage;
import one.moonx.navigation.constant.MessageConstant;
import one.moonx.navigation.pojo.dto.IdsDTO;
import one.moonx.navigation.pojo.dto.NavDTO;
import one.moonx.navigation.pojo.dto.NavQuery;
import one.moonx.navigation.pojo.vo.NavVO;
import one.moonx.navigation.service.NavService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("adminNavController")
@RequestMapping("/admin/nav")
public class NavController {
    @Autowired
    private NavService navService;

    /**
     * 获取导航
     *
     * @return {@link Result }<{@link List }<{@link NavVO }>>
     */
    @GetMapping
    public ResultPage<List<NavVO>> getNav(NavQuery query) {
        ResultPage<List<NavVO>> result = navService.getVOList(query);
        return result;
    }

    /**
     * 按 ID 获取导航
     *
     * @param id id
     * @return {@link Result }<{@link NavVO }>
     */
    @GetMapping("/{id}")
    public Result<NavVO> getNavById(@PathVariable Integer id) {
        NavVO navVO = navService.getVOById(id);
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

    /**
     * 删除多个标签
     *
     * @param ids ids
     * @return {@link Result }<{@link String }>
     */
    @DeleteMapping("/multiple")
    public Result<String> deleteMultipleTags(@RequestBody IdsDTO ids) {
        navService.deleteMultipleTags(ids.getIds());
        return Result.success.msg(MessageConstant.DELETE_SUCCESS);
    }
}
