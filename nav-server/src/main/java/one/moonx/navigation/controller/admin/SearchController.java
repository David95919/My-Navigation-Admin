package one.moonx.navigation.controller.admin;

import one.moonx.navigation.base.Result;
import one.moonx.navigation.constant.MessageConstant;
import one.moonx.navigation.convert.SearchConvert;
import one.moonx.navigation.pojo.vo.SearchVO;
import one.moonx.navigation.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Result<List<SearchVO>> getSearch() {
        List<SearchVO> searchVOList = searchConvert.convertVO(searchService.list());

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
        SearchVO searchVO = searchConvert.convertVO(searchService.getById(id));

        return Result.success.msgAndData(MessageConstant.GET_SUCCESS, searchVO);
    }
}
