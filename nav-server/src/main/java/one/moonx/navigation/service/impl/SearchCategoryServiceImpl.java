package one.moonx.navigation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import one.moonx.navigation.constant.MessageConstant;
import one.moonx.navigation.convert.SearchCategoryConvert;
import one.moonx.navigation.exception.BaseException;
import one.moonx.navigation.mapper.SearchCategoryMapper;
import one.moonx.navigation.pojo.dto.SearchCategoryDTO;
import one.moonx.navigation.pojo.entity.SearchCategory;
import one.moonx.navigation.service.SearchCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public class SearchCategoryServiceImpl extends ServiceImpl<SearchCategoryMapper, SearchCategory> implements SearchCategoryService {
    @Autowired
    private SearchCategoryConvert searchCategoryConvert;

    private static final String cacheName = "SearchCategoryCache";

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
     * @return {@link List }<{@link SearchCategory }>
     */
    @Override
    @Cacheable(cacheNames = cacheName)
    public List<SearchCategory> list() {
        return super.list();
    }

    /**
     * 按 ID 获取
     *
     * @param id id
     * @return {@link SearchCategory }
     */
    @Override
    @Cacheable(cacheNames = cacheName, key = "#id")
    public SearchCategory getById(Serializable id) {
        SearchCategory searchCategory = super.getById(id);

        if (searchCategory == null) throw new BaseException(MessageConstant.SEARCH_CATEGORY_ID_ERROR);

        return searchCategory;
    }

    /**
     * 创建搜索类别
     *
     * @param searchCategoryDTO 搜索类别 DTO
     */
    @Override
    @CacheEvict(cacheNames = cacheName, allEntries = true)
    public void createSearchCategory(SearchCategoryDTO searchCategoryDTO) {
        check(searchCategoryDTO.getName());

        searchCategoryDTO.setId(null);

        SearchCategory searchCategory = searchCategoryConvert.convert(searchCategoryDTO);

        save(searchCategory);
    }
}
