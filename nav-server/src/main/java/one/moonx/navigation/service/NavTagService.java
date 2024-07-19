package one.moonx.navigation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import one.moonx.navigation.pojo.entity.NavTag;

import java.util.List;

public interface NavTagService extends IService<NavTag> {
    void createNavTag(Integer navId, List<Integer> tags);

    void updateNavTag(Integer navId, List<Integer> tags);

    void deleteNavTag(Integer navId);

    boolean NavIsBindTag(Integer navId);
}
