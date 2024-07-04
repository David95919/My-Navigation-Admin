package one.moonx.navigation.convert;

import one.moonx.navigation.pojo.dto.CategoryDTO;
import one.moonx.navigation.pojo.entity.Category;
import one.moonx.navigation.pojo.vo.CategoryVO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryConvert {
    CategoryVO convertVO(Category category);

    List<CategoryVO> convertVO(List<Category> categoryList);

    Category convert(CategoryVO categoryVO);

    Category convert(CategoryDTO categoryDTO);
}
