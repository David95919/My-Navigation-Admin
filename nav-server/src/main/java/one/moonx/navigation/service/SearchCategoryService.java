package one.moonx.navigation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import one.moonx.navigation.pojo.dto.SearchCategoryDTO;
import one.moonx.navigation.pojo.entity.SearchCategory;

public interface SearchCategoryService extends IService<SearchCategory> {
    void createSearchCategory(SearchCategoryDTO searchCategoryDTO);

    void updateSearchCategory(SearchCategoryDTO searchCategoryDTO);
}
