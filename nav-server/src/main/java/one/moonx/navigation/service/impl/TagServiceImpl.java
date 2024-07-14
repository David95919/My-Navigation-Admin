package one.moonx.navigation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import one.moonx.navigation.constant.MessageConstant;
import one.moonx.navigation.convert.TagConvert;
import one.moonx.navigation.exception.BaseException;
import one.moonx.navigation.mapper.TagMapper;
import one.moonx.navigation.pojo.dto.TagDTO;
import one.moonx.navigation.pojo.entity.Tag;
import one.moonx.navigation.pojo.vo.TagVO;
import one.moonx.navigation.service.NavService;
import one.moonx.navigation.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
    @Autowired
    private TagConvert tagConvert;
    @Autowired
    private NavService navService;

    private static final String cacheNames = "TagCache";

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
     * @return {@link Tag }
     */
    @Override
    @Cacheable(cacheNames = cacheNames, key = "#id")
    public Tag getById(Serializable id) {
        Tag tag = super.getById(id);
        if (tag == null) {
            throw new BaseException(MessageConstant.ID_ERROR);
        }
        return tag;
    }

    /**
     * 创建标签
     *
     * @param tagDTO 标签 DTO
     */
    @Override
    public void createTag(TagDTO tagDTO) {
        //检查
        check(tagDTO.getName());
        //清空id
        tagDTO.setId(null);

        Tag tag = tagConvert.convert(tagDTO);
        try {
            //保存
            save(tag);
        } catch (DuplicateKeyException e) {
            //名字重复
            throw new BaseException(MessageConstant.NAME_REPEAT);
        }
    }

    /**
     * 更新标签
     *
     * @param tagDTO 标记 DTO
     */
    @Override
    @CacheEvict(cacheNames = cacheNames, key = "#tagDTO.id")
    public void updateTag(TagDTO tagDTO) {
        ///检查
        check(tagDTO.getName(), tagDTO.getId());

        //判断数据库有没有
        Tag dbTag = getById(tagDTO.getId());
        if (dbTag == null) {
            throw new BaseException(MessageConstant.ID_ERROR);
        }

        Tag tag = tagConvert.convert(tagDTO);
        updateById(tag);
    }

    /**
     * 按 ID 删除
     *
     * @param id id
     * @return boolean
     */
    @Override
    @CacheEvict(cacheNames = cacheNames, key = "#id")
    public boolean removeById(Serializable id) {
        //判断网站是否绑定了这个Tag
        boolean isBindTag = navService.isBindTag(Integer.parseInt(id.toString()));
        if (isBindTag) {
            throw new BaseException(MessageConstant.ALREADY_BIND_NAV);
        }

        boolean result = super.removeById(id);
        if (!result) {
            throw new BaseException(MessageConstant.ID_ERROR);
        }
        return true;
    }

    /**
     * 获取VoList
     *
     * @param idList id列表
     * @return {@link List }<{@link TagVO }>
     */
    @Override
    public List<TagVO> getVOList(List<Integer> idList) {
        ArrayList<TagVO> arrays = new ArrayList(idList.size());

        idList.forEach(tagId -> {
            TagVO tagVO = tagConvert.convertVO(getById(tagId));
            arrays.add(tagVO);
        });

        return arrays;
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
