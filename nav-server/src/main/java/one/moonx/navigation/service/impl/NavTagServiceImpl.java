package one.moonx.navigation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import one.moonx.navigation.mapper.NavTagMapper;
import one.moonx.navigation.pojo.entity.NavTag;
import one.moonx.navigation.service.NavTagService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class NavTagServiceImpl extends ServiceImpl<NavTagMapper, NavTag> implements NavTagService {
    /**
     * 创建NavTag
     *
     * @param navId 导航 ID
     * @param tags  标签
     */
    @Override
    @Transactional
    public void createNavTag(Integer navId, List<Integer> tags) {
        //tags转换成navTagList
        List<NavTag> navTagList = new ArrayList<>();
        tags.forEach(tagId -> {
            navTagList.add(new NavTag(null, navId, tagId));
        });

        //保存
        saveBatch(navTagList);
    }

    /**
     * 更新 NavTag
     *
     * @param navId 导航 ID
     * @param tags  标签
     */
    @Override
    @Transactional
    @CacheEvict(cacheNames = "TagCacheByNavId", key = "#navId")
    public void updateNavTag(Integer navId, List<Integer> tags) {
        //删除之前的navTag
        baseMapper.deleteNavTag(navId);

        //创建
        createNavTag(navId, tags);
    }

    /**
     * 删除 NavTag
     *
     * @param navId 导航 ID
     */
    @Override
    @CacheEvict(cacheNames = "TagCacheByNavId", key = "#navId")
    public void deleteNavTag(Integer navId) {
        //删除
        baseMapper.deleteNavTag(navId);
    }


    /**
     * nav是否绑定标签
     *
     * @param tagId 标记 ID
     * @return boolean
     */
    @Override
    public boolean NavIsBindTag(Integer tagId) {
        Long count = lambdaQuery().eq(NavTag::getTagId, tagId).count();
        return count >= 1;
    }
}
