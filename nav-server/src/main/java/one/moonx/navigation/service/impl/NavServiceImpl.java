package one.moonx.navigation.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import one.moonx.navigation.base.ResultPage;
import one.moonx.navigation.constant.MessageConstant;
import one.moonx.navigation.constant.NavConstant;
import one.moonx.navigation.convert.CategoryConvert;
import one.moonx.navigation.convert.NavConvert;
import one.moonx.navigation.convert.TagConvert;
import one.moonx.navigation.exception.BaseException;
import one.moonx.navigation.mapper.NavMapper;
import one.moonx.navigation.pojo.dto.NavDTO;
import one.moonx.navigation.pojo.dto.NavQuery;
import one.moonx.navigation.pojo.entity.Nav;
import one.moonx.navigation.pojo.vo.NavVO;
import one.moonx.navigation.service.CategoryService;
import one.moonx.navigation.service.NavService;
import one.moonx.navigation.service.NavTagService;
import one.moonx.navigation.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Service
public class NavServiceImpl extends ServiceImpl<NavMapper, Nav> implements NavService {
    @Autowired
    private NavConvert navConvert;
    @Autowired
    private CategoryConvert categoryConvert;
    @Autowired
    private TagConvert tagConvert;

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TagService tagService;
    @Autowired
    private NavTagService navTagService;


    /**
     * 检查
     *
     * @param name 名字
     * @param url  网址
     */
    private void check(String name, String url) {
        //判断名字是否合法
        if (name != null) {
            if (name.isEmpty() || !(name.length() <= 24 && name.length() >= 3)) {
                throw new BaseException(NavConstant.NAME_UNLAWFUL);
            }
        }

        if (url != null) {
            if (!url.matches("^(http|https)://.*$")) {
                throw new BaseException(NavConstant.URL_UNLAWFUL);
            }
        }
    }

    /**
     * 检查
     *
     * @param name     名字
     * @param url      网址
     * @param category 类别
     */
    private void check(String name, String url, Integer category) {
        check(name, url);
        if (category == null) {
            throw new BaseException(NavConstant.CATEGORY_UNLAWFUL);
        }
    }

    /**
     * 按 ID 获取
     *
     * @param id id
     * @return {@link Nav }
     */
    @Override
    public Nav getById(Serializable id) {
        //获取nav
        Nav nav = super.getById(id);

        //如果是空返回id错误
        if (nav == null) throw new BaseException(MessageConstant.ID_ERROR);

        return nav;
    }

    /**
     * 获取Vo按ID获取
     *
     * @param id id
     * @return {@link NavVO }
     */
    @Override
    public NavVO getVOById(Integer id) {
        //判断有没有
        Nav nav = super.getById(id);
        if (nav == null) {
            throw new BaseException(MessageConstant.ID_ERROR);
        }

        NavVO navVO = navConvert.convertVO(nav);

        //获取categoryVO
        navVO.setCategory(categoryConvert.convertVO(categoryService.getById(nav.getCategory())));

        //获取tagVO
        navVO.setTags(tagConvert.convertVO(tagService.listByNavId(nav.getId())));

        return navVO;
    }

    /**
     * 创建导航
     *
     * @param navDTO 导航 DTO
     */
    @Override
    @Transactional
    public void createNav(NavDTO navDTO) {
        //检查
        check(navDTO.getName(), navDTO.getUrl(), navDTO.getCategory());

        //检查标签存不存在
        if (!navDTO.getTags().isEmpty() && !tagService.isTag(navDTO.getTags())) {
            throw new BaseException(MessageConstant.TAG_ID_ERROR);
        }

        //检查分类存不存在
        if (categoryService.getById(navDTO.getCategory()) == null) {
            throw new BaseException(MessageConstant.CATEGORY_ID_ERROR);
        }

        Nav nav = navConvert.convert(navDTO);

        save(nav);

        //保存标签
        navTagService.createNavTag(nav.getId(), navDTO.getTags());
    }

    /**
     * 更新导航
     *
     * @param navDTO 导航 DTO
     */
    @Override
    @Transactional
    public void updateNav(NavDTO navDTO) {
        //检查
        check(navDTO.getName(), navDTO.getUrl());

        //检查标签存不存在
        if (!tagService.isTag(navDTO.getTags())) {
            throw new BaseException(MessageConstant.TAG_ID_ERROR);
        }

        //检查分类存不存在
        if (navDTO.getCategory() == null) {
            throw new BaseException(MessageConstant.NO_BINDING_CATEGORY);
        }

        if (categoryService.getById(navDTO.getCategory()) == null) {
            throw new BaseException(MessageConstant.CATEGORY_ID_ERROR);
        }

        //判断数据库有没有
        Nav dbNav = getById(navDTO.getId());
        if (dbNav == null) {
            throw new BaseException(NavConstant.UPDATE_UNLAWFUL);
        }

        //更新nav
        updateById(navConvert.convert(navDTO));

        //更新navTag
        navTagService.updateNavTag(navDTO.getId(), navDTO.getTags());
    }

    /**
     * 按 ID 删除
     *
     * @param id id
     * @return boolean
     */
    @Override
    public boolean removeById(Serializable id) {
        boolean result = super.removeById(id);
        //判断有没有删除成功
        if (!result) {
            throw new BaseException(MessageConstant.ID_ERROR);
        }
        //删除navTag
        navTagService.deleteNavTag(Integer.parseInt(id.toString()));
        return true;
    }

    /**
     * 是否绑定类别
     *
     * @param id 分类id
     * @return boolean
     */
    @Override
    public boolean isBindCategory(Integer id) {
        Long count = lambdaQuery().eq(Nav::getCategory, id).count();
        return count >= 1;
    }

    /**
     * 是绑定标签
     *
     * @param tagId 标签id
     * @return boolean
     */
    @Override
    public boolean isBindTag(Integer tagId) {
        return navTagService.NavIsBindTag(tagId);
    }


    /**
     * 获取 VoList
     *
     * @param query 查询
     * @return {@link ResultPage }<{@link List }<{@link NavVO }>>
     */
    @Override
    public ResultPage<List<NavVO>> getVOList(NavQuery query) {
        if (query.getSize() == null || query.getCurrent() == null) throw new BaseException(MessageConstant.QUERY_ERROR);

        // TODO AddTag Query
        Page<Nav> page = lambdaQuery()
                .like(query.getName() != null, Nav::getName, query.getName())
                .eq(query.getCategory() != null, Nav::getCategory, query.getCategory())
                .page(Page.of(query.getCurrent(), query.getSize()));

        List<Nav> records = page.getRecords();

        List<NavVO> navVOList = navConvert.convertVO(records);

        for (int i = 0; i < records.size(); i++) {
            Nav nav = records.get(i);
            //获取分类
            Integer categoryId = nav.getCategory();
            navVOList.get(i).setCategory(categoryConvert.convertVO(categoryService.getById(categoryId)));

            //获取tags
            navVOList.get(i).setTags(tagConvert.convertVO(tagService.listByNavId(nav.getId())));
        }

        return ResultPage.success.msgAndRecords(MessageConstant.GET_SUCCESS, navVOList, page.getTotal());
    }

    /**
     * 删除多个标签
     *
     * @param ids ids
     */
    @Override
    public void deleteMultipleTags(List<Integer> ids) {
        //TODO removeBatchByIds(ids);
        for (Integer id : ids) {
            removeById(id);
        }
    }
}
