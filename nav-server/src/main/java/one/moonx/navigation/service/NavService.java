package one.moonx.navigation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import one.moonx.navigation.pojo.dto.NavDTO;
import one.moonx.navigation.pojo.dto.NavQuery;
import one.moonx.navigation.pojo.entity.Nav;
import one.moonx.navigation.pojo.vo.NavVO;

import java.util.List;


public interface NavService extends IService<Nav> {
    void createNav(NavDTO navDTO);

    void updateNav(NavDTO navDTO);

    boolean isBindCategory(Integer id);

    boolean isBindTag(Integer id);

    List<NavVO> getVOList(NavQuery query);

    NavVO getVOById(Integer id);
}
