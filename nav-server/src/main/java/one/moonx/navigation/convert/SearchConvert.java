package one.moonx.navigation.convert;

import one.moonx.navigation.pojo.entity.Search;
import one.moonx.navigation.pojo.vo.SearchVO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SearchConvert {
    SearchVO convertVO(Search search);

    List<SearchVO> convertVO(List<Search> searchList);
}
