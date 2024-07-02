package one.moonx.navigation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import one.moonx.navigation.constant.MessageConstant;
import one.moonx.navigation.exception.BaseException;
import one.moonx.navigation.mapper.TagMapper;
import one.moonx.navigation.pojo.entity.Tag;
import one.moonx.navigation.service.TagService;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
    /**
     * 按 ID 获取
     *
     * @param id id
     * @return {@link Tag }
     */
    @Override
    public Tag getById(Serializable id) {
        Tag tag = super.getById(id);
        if (tag==null){
            throw new BaseException(MessageConstant.ID_ERROR);
        }
        return tag;
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
