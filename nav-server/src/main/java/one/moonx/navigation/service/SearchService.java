package one.moonx.navigation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import one.moonx.navigation.pojo.dto.SearchDTO;
import one.moonx.navigation.pojo.dto.SearchQuery;
import one.moonx.navigation.pojo.entity.Search;
import one.moonx.navigation.pojo.vo.SearchVO;

import java.util.List;

public interface SearchService extends IService<Search> {
    void createSearch(SearchDTO searchDTO);

    List<SearchVO> getVOList(SearchQuery query);

    SearchVO getVOById(String id);

    void updateSearch(SearchDTO searchDTO);
}
