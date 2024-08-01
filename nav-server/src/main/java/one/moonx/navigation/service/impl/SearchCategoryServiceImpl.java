package one.moonx.navigation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import one.moonx.navigation.constant.MessageConstant;
import one.moonx.navigation.exception.BaseException;
import one.moonx.navigation.mapper.SearchCategoryMapper;
import one.moonx.navigation.pojo.entity.SearchCategory;
import one.moonx.navigation.service.SearchCategoryService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public class SearchCategoryServiceImpl extends ServiceImpl<SearchCategoryMapper, SearchCategory> implements SearchCategoryService {

    private static final String cacheName = "SearchCategoryCache";

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
}
