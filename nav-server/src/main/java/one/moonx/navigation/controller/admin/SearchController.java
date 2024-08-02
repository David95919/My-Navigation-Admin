package one.moonx.navigation.controller.admin;

import one.moonx.navigation.base.Result;
import one.moonx.navigation.constant.MessageConstant;
import one.moonx.navigation.convert.SearchConvert;
import one.moonx.navigation.pojo.dto.SearchDTO;
import one.moonx.navigation.pojo.dto.SearchQuery;
import one.moonx.navigation.pojo.vo.SearchVO;
import one.moonx.navigation.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("adminSearchController")
@RequestMapping("/admin/search")
public class SearchController {
    @Autowired
    private SearchService searchService;
    @Autowired
    private SearchConvert searchConvert;

    /**
     * 获取搜索
     *
     * @return {@link Result }<{@link List }<{@link SearchVO }>>
     */
    @GetMapping
    public Result<List<SearchVO>> getSearch(SearchQuery query) {
        List<SearchVO> searchVOList = searchService.getVOList(query);

        return Result.success.msgAndData(MessageConstant.GET_SUCCESS, searchVOList);
    }

    /**
     * 按 ID 搜索
     *
     * @param id id
     * @return {@link Result }<{@link SearchVO }>
     */
    @GetMapping("/{id}")
    public Result<SearchVO> getSearchById(@PathVariable String id) {
        SearchVO searchVO = searchService.getVOById(id);

        return Result.success.msgAndData(MessageConstant.GET_SUCCESS, searchVO);
    }

    /**
     * 创建搜索
     *
     * @param searchDTO 搜索 DTO
     * @return {@link Result }<{@link String }>
     */
    @PostMapping
    public Result<String> createSearch(@RequestBody SearchDTO searchDTO) {
        searchService.createSearch(searchDTO);

        return Result.success.msg(MessageConstant.ADD_SUCCESS);
    }

    /**
     * 更新搜索
     *
     * @param searchDTO 搜索 DTO
     * @return {@link Result }<{@link String }>
     */
    @PutMapping
    public Result<String> updateSearch(@RequestBody SearchDTO searchDTO) {
        searchService.updateSearch(searchDTO);

        return Result.success.msg(MessageConstant.UPDATE_SUCCESS);
    }
}
