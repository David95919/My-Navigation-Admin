package one.moonx.navigation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import one.moonx.navigation.constant.MessageConstant;
import one.moonx.navigation.convert.CategoryConvert;
import one.moonx.navigation.exception.BaseException;
import one.moonx.navigation.mapper.CategoryMapper;
import one.moonx.navigation.pojo.dto.CategoryDTO;
import one.moonx.navigation.pojo.entity.Category;
import one.moonx.navigation.pojo.vo.CategoryVO;
import one.moonx.navigation.service.CategoryService;
import one.moonx.navigation.service.NavService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private CategoryConvert categoryConvert;
    @Autowired
    private NavService navService;

    /**
     * 检查
     *
     * @param name 名字
     */
    private void check(String name) {
        if (name != null) {
            if (name.isEmpty() || !(name.length() <= 24 && name.length() >= 3)) {
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
     * 按 ID 获取
     *
     * @param id id
     * @return {@link Category }
     */
    @Override
    public Category getById(Serializable id) {
        Category category = CategoryService.super.getById(id);
        if (category == null) {
            throw new BaseException(MessageConstant.ID_ERROR);
        }
        return category;
    }

    /**
     * 创建类别
     *
     * @param categoryDTO 类别 DTO
     */
    @Override
    public void createCategory(CategoryDTO categoryDTO) {
        //检查名字
        check(categoryDTO.getName());
        //清空id
        categoryDTO.setId(null);
        //保存
        Category category = categoryConvert.convert(categoryDTO);

        try {
            //保存
            save(category);
        } catch (UncategorizedSQLException e) {
            //名字重复
            throw new BaseException(MessageConstant.NAME_REPEAT);
        }
    }

    @Override
    public void updateCategory(CategoryDTO categoryDTO) {
        //简单检查
        check(categoryDTO.getName(), categoryDTO.getId());

        //检查数据库有没有数据
        Category dbCategory = getById(categoryDTO.getId());
        if (dbCategory == null) {
            throw new BaseException(MessageConstant.ID_ERROR);
        }

        //保存
        Category category = categoryConvert.convert(categoryDTO);
        updateById(category);
    }

    /**
     * 按 ID 删除
     *
     * @param id id
     * @return boolean
     */
    @Override
    public boolean removeById(Serializable id) {
        //判断网站是否绑定了这个分类
        boolean isBindCategory = navService.isBindCategory(Integer.parseInt(id.toString()));
        if (isBindCategory) {
            throw new BaseException(MessageConstant.ALREADY_BIND_NAV);
        }

        boolean result = super.removeById(id);

        if (!result) {
            throw new BaseException(MessageConstant.ID_ERROR);
        }
        return true;
    }

    /**
     * 获取Vo按id
     *
     * @param id id
     * @return {@link CategoryVO }
     */
    @Override
    public CategoryVO getVOById(Integer id) {
        return categoryConvert.convertVO(getById(id));
    }
}
