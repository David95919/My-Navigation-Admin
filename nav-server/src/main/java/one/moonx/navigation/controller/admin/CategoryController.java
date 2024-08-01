package one.moonx.navigation.controller.admin;

import one.moonx.navigation.base.Result;
import one.moonx.navigation.constant.MessageConstant;
import one.moonx.navigation.convert.CategoryConvert;
import one.moonx.navigation.pojo.dto.CategoryDTO;
import one.moonx.navigation.pojo.dto.IdsDTO;
import one.moonx.navigation.pojo.vo.CategoryVO;
import one.moonx.navigation.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("adminCategoryController")
@RequestMapping("/admin/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryConvert categoryConvert;

    /**
     * 获取类别
     *
     * @return {@link Result }<{@link List }<{@link CategoryVO }>>
     */
    @GetMapping
    public Result<List<CategoryVO>> getCategory() {
        List<CategoryVO> categoryVOList = categoryConvert.convertVO(categoryService.list());

        return Result.success.msgAndData(MessageConstant.GET_SUCCESS, categoryVOList);
    }

    /**
     * 按 ID 获取类别
     *
     * @param id id
     * @return {@link Result }<{@link CategoryVO }>
     */
    @GetMapping("/{id}")
    public Result<CategoryVO> getCategoryById(@PathVariable int id) {
        CategoryVO categoryVO = categoryConvert.convertVO(categoryService.getById(id));
        return Result.success.msgAndData(MessageConstant.GET_SUCCESS, categoryVO);
    }

    /**
     * 创建类别
     *
     * @param categoryDTO 类别 DTO
     * @return {@link Result }
     */
    @PostMapping
    public Result<String> createCategory(@RequestBody CategoryDTO categoryDTO) {
        categoryService.createCategory(categoryDTO);
        return Result.success.msg(MessageConstant.ADD_SUCCESS);
    }

    /**
     * 更新类别
     *
     * @param categoryDTO 类别 DTO
     * @return {@link Result }
     */
    @PutMapping
    public Result<String> updateCategory(@RequestBody CategoryDTO categoryDTO) {
        categoryService.updateCategory(categoryDTO);
        return Result.success.msg(MessageConstant.UPDATE_SUCCESS);
    }

    /**
     * 删除类别
     *
     * @param id id
     * @return {@link Result }
     */
    @DeleteMapping("/{id}")
    public Result<String> deleteCategory(@PathVariable Integer id) {
        categoryService.removeById(id);
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
        categoryService.deleteMultipleTags(ids.getIds());
        return Result.success.msg(MessageConstant.DELETE_SUCCESS);
    }
}
