package one.moonx.navigation.convert;

import one.moonx.navigation.pojo.dto.SearchCategoryDTO;
import one.moonx.navigation.pojo.entity.SearchCategory;
import one.moonx.navigation.pojo.vo.SearchCategoryVO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SearchCategoryConvert {
    SearchCategoryVO convertVO(SearchCategory searchCategory);

    List<SearchCategoryVO> convertVO(List<SearchCategory> searchCategoryList);

    SearchCategory convert(SearchCategoryDTO searchCategoryDTO);
}
