package one.moonx.navigation.controller.admin;

import one.moonx.navigation.base.Result;
import one.moonx.navigation.constant.MessageConstant;
import one.moonx.navigation.convert.SearchCategoryConvert;
import one.moonx.navigation.pojo.dto.SearchCategoryDTO;
import one.moonx.navigation.pojo.vo.SearchCategoryVO;
import one.moonx.navigation.service.SearchCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("adminSearchCategoryController")
@RequestMapping("/admin/search/category")
public class SearchCategoryController {
    @Autowired
    private SearchCategoryService searchCategoryService;
    @Autowired
    private SearchCategoryConvert searchCategoryConvert;

    /**
     * 获取搜索类别
     *
     * @return {@link Result }<{@link List }<{@link SearchCategoryVO }>>
     */
    @GetMapping
    public Result<List<SearchCategoryVO>> getSearchCategory() {
        List<SearchCategoryVO> searchCategoryVOList = searchCategoryConvert.convertVO(searchCategoryService.list());

        return Result.success.msgAndData(MessageConstant.GET_SUCCESS, searchCategoryVOList);
    }

    /**
     * 按 ID 获取搜索类别
     *
     * @param id id
     * @return {@link Result }<{@link SearchCategoryVO }>
     */
    @GetMapping("/{id}")
    public Result<SearchCategoryVO> getSearchCategoryById(@PathVariable Integer id) {
        SearchCategoryVO searchCategoryVO = searchCategoryConvert.convertVO(searchCategoryService.getById(id));

        return Result.success.msgAndData(MessageConstant.GET_SUCCESS, searchCategoryVO);
    }

    /**
     * 创建搜索类别
     *
     * @param searchCategoryDTO 搜索类别 DTO
     * @return {@link Result }<{@link String }>
     */
    @PostMapping
    public Result<String> createSearchCategory(@RequestBody SearchCategoryDTO searchCategoryDTO) {
        searchCategoryService.createSearchCategory(searchCategoryDTO);

        return Result.success.msg(MessageConstant.ADD_SUCCESS);
    }

    /**
     * 更新搜索类别
     *
     * @param searchCategoryDTO 搜索类别 DTO
     * @return {@link Result }<{@link String }>
     */
    @PutMapping
    public Result<String> updateSearchCategory(@RequestBody SearchCategoryDTO searchCategoryDTO) {
        searchCategoryService.updateSearchCategory(searchCategoryDTO);

        return Result.success.msg(MessageConstant.UPDATE_SUCCESS);
    }

    /**
     * 删除搜索类别
     *
     * @param id id
     * @return {@link Result }<{@link String }>
     */
    @DeleteMapping("/{id}")
    public Result<String> deleteSearchCategory(@PathVariable Integer id) {
        searchCategoryService.deleteSearchCategory(id);

        return Result.success.msg(MessageConstant.DELETE_SUCCESS);
    }
}
