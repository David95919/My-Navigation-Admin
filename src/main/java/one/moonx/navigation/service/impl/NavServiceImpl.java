package one.moonx.navigation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import one.moonx.navigation.constant.MessageConstant;
import one.moonx.navigation.constant.NavConstant;
import one.moonx.navigation.convert.CategoryConvert;
import one.moonx.navigation.convert.NavConvert;
import one.moonx.navigation.convert.TagConvert;
import one.moonx.navigation.exception.BaseException;
import one.moonx.navigation.mapper.NavMapper;
import one.moonx.navigation.pojo.dto.NavDTO;
import one.moonx.navigation.pojo.entity.Category;
import one.moonx.navigation.pojo.entity.Nav;
import one.moonx.navigation.pojo.vo.NavVO;
import one.moonx.navigation.pojo.vo.TagVO;
import one.moonx.navigation.service.CategoryService;
import one.moonx.navigation.service.NavService;
import one.moonx.navigation.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Service
public class NavServiceImpl extends ServiceImpl<NavMapper, Nav> implements NavService {
    @Autowired
    private NavConvert navConvert;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TagService tagService;

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
        //TODO 需要更好的判断url
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
        Nav nav = super.getById(id);
        if (nav == null) {
            throw new BaseException(MessageConstant.ID_ERROR);
        }

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

        //处理分类
        navVO.setCategory(categoryService.getVOById(nav.getCategory()));

        //处理tag 这么写是为了命中缓存
        navVO.setTags(tagService.getVOList(nav.getTags()));

        return navVO;
    }

    /**
     * 创建导航
     *
     * @param navDTO 导航 DTO
     */
    @Override
    public void createNav(NavDTO navDTO) {
        //检查
        check(navDTO.getName(), navDTO.getUrl(), navDTO.getCategory());

        Nav nav = navConvert.convert(navDTO);
        try {
            //保存
            save(nav);
        } catch (UncategorizedSQLException e) {
            //名字重复
            throw new BaseException(MessageConstant.NAME_REPEAT);
        }
    }

    /**
     * 更新导航
     *
     * @param navDTO 导航 DTO
     */
    @Override
    public void updateNav(NavDTO navDTO) {
        //检查
        check(navDTO.getName(), navDTO.getUrl());

        //判断数据库有没有
        Nav dbNav = getById(navDTO.getId());
        if (dbNav == null) {
            throw new BaseException(NavConstant.UPDATE_UNLAWFUL);
        }
        //更新
        updateById(navConvert.convert(navDTO));
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
        if (!result) {
            throw new BaseException(MessageConstant.ID_ERROR);
        }
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
     * @param id 标签id
     * @return boolean
     */
    @Override
    public boolean isBindTag(Integer id) {
        //TODO 添加一个tag多对一的表

        //sql安全的:)
        Long count = lambdaQuery()
                .apply("JSON_CONTAINS(tags, CONCAT('[', {0}, ']'))", id)
                .count();
        return count >= 1;
    }

    /**
     * 获取 VoList
     *
     * @return {@link List }<{@link NavVO }>
     */
    @Override
    public List<NavVO> getVOList() {
        List<Nav> navList = list();
        List<NavVO> navVOList = navConvert.convertVO(navList);

        for (int i = 0; i < navList.size(); i++) {
            //处理分类
            Integer categoryId = navList.get(i).getCategory();
            navVOList.get(i).setCategory(categoryService.getVOById(categoryId));

            //处理tags
            List<Integer> tags = navList.get(i).getTags();
            navVOList.get(i).setTags(tagService.getVOList(tags));
        }
        return navVOList;
    }
}
