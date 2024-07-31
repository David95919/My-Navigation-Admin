package one.moonx.navigation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import one.moonx.navigation.constant.MessageConstant;
import one.moonx.navigation.convert.TagConvert;
import one.moonx.navigation.exception.BaseException;
import one.moonx.navigation.mapper.TagMapper;
import one.moonx.navigation.pojo.dto.TagDTO;
import one.moonx.navigation.pojo.entity.NavTag;
import one.moonx.navigation.pojo.entity.Tag;
import one.moonx.navigation.service.NavService;
import one.moonx.navigation.service.NavTagService;
import one.moonx.navigation.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
    @Autowired
    private NavTagService navTagService;

    private static final String cacheName = "TagCache";

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
     * @return {@link List }<{@link Tag }>
     */
    @Override
    @Cacheable(cacheNames = cacheName)
    public List<Tag> list() {
        return super.list();
    }

    /**
     * 按 ID 获取
     *
     * @param id id
     * @return {@link Tag }
     */
    @Override
    @Cacheable(cacheNames = cacheName, key = "#id")
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
    @CacheEvict(cacheNames = cacheName, allEntries = true)
    public void createTag(TagDTO tagDTO) {
        //检查
        check(tagDTO.getName());

        //清空id
        tagDTO.setId(null);

        //转换成entity
        Tag tag = tagConvert.convert(tagDTO);

        //保存
        save(tag);
    }

    /**
     * 更新标签
     *
     * @param tagDTO 标记 DTO
     */
    @Override
    @CacheEvict(cacheNames = cacheName, allEntries = true)
    public void updateTag(TagDTO tagDTO) {
        ///检查
        check(tagDTO.getName(), tagDTO.getId());

        //转换成entity
        Tag tag = tagConvert.convert(tagDTO);

        //更新
        boolean result = updateById(tag);

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
        //判断标签是否绑定网站
        boolean isBindTag = navService.isBindTag(Integer.parseInt(id.toString()));
        if (isBindTag) {
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
     * 按NavId获取TagList
     *
     * @param navId 导航 ID
     * @return {@link List }<{@link Tag }>
     */
    @Override
    @Cacheable(cacheNames = cacheName + "ByNavId", key = "#navId")
    public List<Tag> listByNavId(Integer navId) {
        //获取nav一对多数据
        List<NavTag> list = navTagService.lambdaQuery().eq(NavTag::getNavId, navId).list();

        //没有绑定标签
        if (list.isEmpty()) return new ArrayList<>();

        //转换成tagIds
        List<Integer> tagIds = list.stream()
                .map(NavTag::getTagId)
                .toList();

        //in 查询多个符合的
        return lambdaQuery().in(Tag::getId, tagIds).list();
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

    /**
     * 检查标签存不存在
     *
     * @param ids ids
     * @return boolean
     */
    @Override
    public boolean isTag(List<Integer> ids) {
        if (ids.isEmpty()) return true;

        List<Tag> tags = baseMapper.selectBatchIds(ids);

        return tags.size() == ids.size();
    }
}
