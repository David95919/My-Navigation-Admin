package one.moonx.navigation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import one.moonx.navigation.constant.MessageConstant;
import one.moonx.navigation.constant.NavConstant;
import one.moonx.navigation.convert.SearchCategoryConvert;
import one.moonx.navigation.convert.SearchConvert;
import one.moonx.navigation.exception.BaseException;
import one.moonx.navigation.mapper.SearchMapper;
import one.moonx.navigation.pojo.dto.SearchDTO;
import one.moonx.navigation.pojo.dto.SearchQuery;
import one.moonx.navigation.pojo.entity.Search;
import one.moonx.navigation.pojo.entity.SearchCategory;
import one.moonx.navigation.pojo.vo.SearchCategoryVO;
import one.moonx.navigation.pojo.vo.SearchVO;
import one.moonx.navigation.service.SearchCategoryService;
import one.moonx.navigation.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public class SearchServiceImpl extends ServiceImpl<SearchMapper, Search> implements SearchService {
    @Autowired
    private SearchConvert searchConvert;
    @Autowired
    private SearchCategoryService searchCategoryService;
    @Autowired
    private SearchCategoryConvert searchCategoryConvert;

    private static final String cacheName = "SearchCache";

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
     * 检查
     *
     * @param name             名字
     * @param searchCategoryId 搜索类别 ID
     * @param url              网址
     */
    private void check(String name, Integer searchCategoryId, String url) {
        check(name);

        //搜索类别id是否为空
        if (searchCategoryId == null) {
            throw new BaseException(MessageConstant.SEARCH_CATEGORY_ID_ERROR);
        }
        //判断搜索类别是否查找
        if (searchCategoryService.getById(searchCategoryId) == null) {
            throw new BaseException(MessageConstant.SEARCH_CATEGORY_ID_ERROR);
        }

        //检查url
        if (url != null) {
            if (!url.matches("^(http|https)://.*$")) {
                throw new BaseException(NavConstant.URL_UNLAWFUL);
            }
        }
    }

    /**
     * 检查
     *
     * @param name             名字
     * @param id               id
     * @param searchCategoryId 搜索类别 ID
     * @param url              网址
     */
    private void check(String name, Integer id, Integer searchCategoryId, String url) {
        check(name, searchCategoryId, url);

        if (searchCategoryId == null) {
            throw new BaseException(MessageConstant.SEARCH_ID_ERROR);
        }
    }

    /**
     * 获取 VOList
     *
     * @param query 查询
     * @return {@link List }<{@link SearchVO }>
     */
    @Override
    @Cacheable(cacheNames = cacheName)
    public List<SearchVO> getVOList(SearchQuery query) {
        List<Search> searchList = lambdaQuery()
                .eq(query.getSearchCategoryId() != null, Search::getCategoryId, query.getSearchCategoryId())
                .list();

        List<SearchVO> searchVOList = searchConvert.convertVO(searchList);

        //处理搜索分类VO
        for (int i = 0; i < searchList.size(); i++) {
            Search search = searchList.get(i);

            //获取搜索分类
            SearchCategory searchCategory = searchCategoryService.getById(search.getCategoryId());
            //搜索分类转换成VO
            SearchCategoryVO searchCategoryVO = searchCategoryConvert.convertVO(searchCategory);

            searchVOList.get(i).setSearchCategory(searchCategoryVO);
        }

        return searchVOList;
    }

    @Override
    @Cacheable(cacheNames = cacheName, key = "#id")
    public SearchVO getVOById(String id) {
        Search search = getById(id);

        //转换成VO
        SearchVO searchVO = searchConvert.convertVO(search);

        //获取搜索分类
        SearchCategory searchCategory = searchCategoryService.getById(search.getCategoryId());
        searchVO.setSearchCategory(searchCategoryConvert.convertVO(searchCategory));

        return searchVO;
    }

    /**
     * 按 ID 获取
     *
     * @param id id
     * @return {@link Search }
     */
    @Override
    public Search getById(Serializable id) {
        Search search = super.getById(id);

        if (search == null) throw new BaseException(MessageConstant.SEARCH_ID_ERROR);

        return search;
    }

    /**
     * 创建搜索
     *
     * @param searchDTO 搜索 DTO
     */
    @Override
    @CacheEvict(cacheNames = cacheName, allEntries = true)
    public void createSearch(SearchDTO searchDTO) {
        check(searchDTO.getName(), searchDTO.getCategoryId(), searchDTO.getUrl());

        searchDTO.setId(null);

        Search search = searchConvert.convert(searchDTO);

        save(search);
    }

    /**
     * 更新搜索
     *
     * @param searchDTO 搜索 DTO
     */
    @Override
    @CacheEvict(cacheNames = cacheName, allEntries = true)
    public void updateSearch(SearchDTO searchDTO) {
        check(searchDTO.getName(), searchDTO.getId(), searchDTO.getCategoryId(), searchDTO.getUrl());

        Search search = searchConvert.convert(searchDTO);

        boolean result = updateById(search);

        if (!result) throw new BaseException(MessageConstant.UPDATE_FAIL);
    }
}
