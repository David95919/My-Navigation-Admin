package one.moonx.navigation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import one.moonx.navigation.constant.MessageConstant;
import one.moonx.navigation.convert.CategoryConvert;
import one.moonx.navigation.exception.BaseException;
import one.moonx.navigation.mapper.CategoryMapper;
import one.moonx.navigation.pojo.dto.CategoryDTO;
import one.moonx.navigation.pojo.entity.Category;
import one.moonx.navigation.service.CategoryService;
import one.moonx.navigation.service.NavService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private CategoryConvert categoryConvert;
    @Autowired
    private NavService navService;

    private static final String cacheName = "CategoryCache";

    /**
     * 检查
     *
     * @param name 名字
     */
    private void check(String name) {
        if (name != null) {
            if (name.isEmpty() || !(name.length() <= 24 && name.length() >= 2)) {
                throw new BaseException(MessageConstant.NAME_UNLAWFUL);
            }
        }
    }

    /**
     * 检查
     *
     * @param name 名字
     * @param id   id
     */
    private void check(String name, Integer id) {
        check(name);
        if (id == null) {
            throw new BaseException(MessageConstant.ID_ERROR);
        }
    }

    /**
     * 列表
     *
     * @return {@link List }<{@link Category }>
     */
    @Override
    @Cacheable(cacheNames = cacheName)
    public List<Category> list() {
        return super.list();
    }

    /**
     * 按 ID 获取
     *
     * @param id id
     * @return {@link Category }
     */
    @Override
    @Cacheable(cacheNames = cacheName, key = "#id")
    public Category getById(Serializable id) {
        Category category = CategoryService.super.getById(id);
        if (category == null) {
            throw new BaseException(MessageConstant.CATEGORY_ID_ERROR);
        }
        return category;
    }

    /**
     * 创建类别
     *
     * @param categoryDTO 类别 DTO
     */
    @Override
    @CacheEvict(cacheNames = cacheName, allEntries = true)
    public void createCategory(CategoryDTO categoryDTO) {
        //检查名字
        check(categoryDTO.getName());

        //清空id
        categoryDTO.setId(null);

        //转换成entity
        Category category = categoryConvert.convert(categoryDTO);

        //保存
        save(category);
    }

    @Override
    @CacheEvict(cacheNames = cacheName, allEntries = true)
    public void updateCategory(CategoryDTO categoryDTO) {
        //检查
        check(categoryDTO.getName(), categoryDTO.getId());

        //转换成entity
        Category category = categoryConvert.convert(categoryDTO);

        //更新
        boolean result = updateById(category);

        //判断有没有保存成功
        if (!result) {
            throw new BaseException(MessageConstant.ID_ERROR);
        }
    }

    /**
     * 按 ID 删除
     *
     * @param id id
     * @return boolean
     */
    @Override
    @CacheEvict(cacheNames = cacheName, key = "#id")
    public boolean removeById(Serializable id) {
        //判断分类是否绑定网站
        boolean isBindCategory = navService.isBindCategory(Integer.parseInt(id.toString()));
        if (isBindCategory) {
            throw new BaseException(MessageConstant.ALREADY_BIND_NAV);
        }

        //删除
        boolean result = super.removeById(id);

        //判断是否删除成功
        if (!result) {
            throw new BaseException(MessageConstant.ID_ERROR);
        }

        return true;
    }

    /**
     * 删除多个标签
     *
     * @param ids ids
     */
    @Override
    @CacheEvict(cacheNames = cacheName, allEntries = true)
    public void deleteMultipleTags(List<Integer> ids) {
        //TODO removeBatchByIds(ids);
        for (Integer id : ids) {
            removeById(id);
        }
    }
}
