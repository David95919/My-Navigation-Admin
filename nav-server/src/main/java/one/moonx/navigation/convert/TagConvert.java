package one.moonx.navigation.convert;

import one.moonx.navigation.pojo.dto.TagDTO;
import one.moonx.navigation.pojo.entity.Tag;
import one.moonx.navigation.pojo.vo.TagVO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagConvert {
    TagVO convertVO(Tag tag);

    List<TagVO> convertVO(List<Tag> tagList);

    Tag convert(TagVO tagVO);

    Tag convert(TagDTO tagDTO);
}
