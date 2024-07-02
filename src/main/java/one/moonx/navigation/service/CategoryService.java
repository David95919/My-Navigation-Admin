package one.moonx.navigation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import one.moonx.navigation.pojo.dto.CategoryDTO;
import one.moonx.navigation.pojo.entity.Category;

public interface CategoryService extends IService<Category> {
    void createCategory(CategoryDTO categoryDTO);

    void updateCategory(CategoryDTO categoryDTO);
}
