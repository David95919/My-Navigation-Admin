package one.moonx.navigation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import one.moonx.navigation.pojo.dto.TagDTO;
import one.moonx.navigation.pojo.entity.Tag;
import one.moonx.navigation.pojo.vo.TagVO;

import java.util.List;

public interface TagService extends IService<Tag> {
    void createTag(TagDTO tagDTO);

    void updateTag(TagDTO tagDTO);

    List<TagVO> getVOList(List<Integer> idList);
}
