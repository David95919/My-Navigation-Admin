package one.moonx.navigation.controller.user;

import one.moonx.navigation.base.Result;
import one.moonx.navigation.constant.MessageConstant;
import one.moonx.navigation.convert.CategoryConvert;
import one.moonx.navigation.pojo.vo.CategoryVO;
import one.moonx.navigation.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userCategoryController")
@RequestMapping("/user/category")
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
}
