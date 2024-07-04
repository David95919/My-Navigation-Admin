package one.moonx.navigation.convert;

import one.moonx.navigation.pojo.dto.NavDTO;
import one.moonx.navigation.pojo.entity.Nav;
import one.moonx.navigation.pojo.vo.CategoryVO;
import one.moonx.navigation.pojo.vo.NavVO;
import one.moonx.navigation.pojo.vo.TagVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface NavConvert {
    @Mappings({
            @Mapping(target = "tags", qualifiedByName = "getTags"),
            @Mapping(target = "category", qualifiedByName = "getCategory")
    })
    List<NavVO> convertVO(List<Nav> navs);

    @Mappings({
            @Mapping(target = "tags", qualifiedByName = "getTags"),
            @Mapping(target = "category", qualifiedByName = "getCategory")
    })
    NavVO convertVO(Nav navs);

    Nav convert(NavDTO navDTO);

    @Named("getTags")
    default List<TagVO> getTags(List<Integer> list) {
        return new ArrayList<TagVO>();
    }

    @Named("getCategory")
    default CategoryVO getCategory(Integer integer) {
        return new CategoryVO();
    }
}
