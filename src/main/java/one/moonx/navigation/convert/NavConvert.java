package one.moonx.navigation.convert;

import one.moonx.navigation.pojo.dto.NavDTO;
import one.moonx.navigation.pojo.entity.Nav;
import one.moonx.navigation.pojo.vo.NavVO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NavConvert {
    List<NavVO> convertVO(List<Nav> navs);
    NavVO convertVO(Nav navs);
    Nav convert(NavDTO navDTO);
}
