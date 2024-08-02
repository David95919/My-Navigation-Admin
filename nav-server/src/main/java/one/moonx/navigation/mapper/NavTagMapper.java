package one.moonx.navigation.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import one.moonx.navigation.pojo.entity.NavTag;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NavTagMapper extends BaseMapper<NavTag> {
    @Delete("DELETE FROM t_nav_tag WHERE nav_id = #{navId}")
    void deleteNavTag(Integer navId);
}
