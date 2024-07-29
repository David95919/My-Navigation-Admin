package one.moonx.navigation.controller.user;

import one.moonx.navigation.base.Result;
import one.moonx.navigation.base.ResultPage;
import one.moonx.navigation.pojo.dto.NavQuery;
import one.moonx.navigation.pojo.vo.NavVO;
import one.moonx.navigation.service.NavService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userNavController")
@RequestMapping("/user/nav")
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
        query.setName(null);
        query.setTag(null);

        ResultPage<List<NavVO>> result = navService.getVOList(query);

        return result;
    }
}
