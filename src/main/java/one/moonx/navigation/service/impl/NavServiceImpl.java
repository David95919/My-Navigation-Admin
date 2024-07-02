package one.moonx.navigation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import one.moonx.navigation.constant.MessageConstant;
import one.moonx.navigation.constant.NavConstant;
import one.moonx.navigation.convert.NavConvert;
import one.moonx.navigation.exception.BaseException;
import one.moonx.navigation.mapper.NavMapper;
import one.moonx.navigation.pojo.dto.NavDTO;
import one.moonx.navigation.pojo.entity.Nav;
import one.moonx.navigation.service.NavService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class NavServiceImpl extends ServiceImpl<NavMapper, Nav> implements NavService {
    @Autowired
    NavConvert navConvert;

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
     * 创建导航
     *
     * @param navDTO 导航 DTO
     */
    @Override
    public void createNav(NavDTO navDTO) {
        check(navDTO.getName(), navDTO.getUrl(), navDTO.getCategory());

        Nav nav = navConvert.convert(navDTO);
        try {
            //保存
            save(nav);
        } catch (UncategorizedSQLException e) {
            //名字重复
            throw new BaseException(NavConstant.NAME_REPEAT);
        }
    }

    @Override
    public void updateNav(NavDTO navDTO) {
        check(navDTO.getName(), navDTO.getUrl());
        Nav dbNav = getById(navDTO.getId());
        if (dbNav == null) {
            throw new BaseException(NavConstant.UPDATE_UNLAWFUL);
        }
        System.out.println(navDTO);
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
}
