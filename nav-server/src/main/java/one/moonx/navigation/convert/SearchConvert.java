package one.moonx.navigation.convert;

import one.moonx.navigation.pojo.dto.SearchDTO;
import one.moonx.navigation.pojo.entity.Search;
import one.moonx.navigation.pojo.vo.SearchCategoryVO;
import one.moonx.navigation.pojo.vo.SearchVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SearchConvert {

    @Mapping(target = "searchCategory", source = "categoryId", qualifiedByName = "getSearchCategory")
    SearchVO convertVO(Search search);

    @Mapping(target = "searchCategory", source = "categoryId", qualifiedByName = "getSearchCategory")
    List<SearchVO> convertVO(List<Search> searchList);

    Search convert(SearchDTO searchDTO);

    @Named("getSearchCategory")
    default SearchCategoryVO getSearchCategory(Integer integer) {
        return new SearchCategoryVO();
    }
}
