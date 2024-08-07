package one.moonx.navigation.controller.user;

import one.moonx.navigation.base.Result;
import one.moonx.navigation.constant.MessageConstant;
import one.moonx.navigation.pojo.dto.SearchQuery;
import one.moonx.navigation.pojo.vo.SearchVO;
import one.moonx.navigation.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userSearchController")
@RequestMapping("/user/search")
public class SearchController {
    @Autowired
    private SearchService searchService;

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
}
