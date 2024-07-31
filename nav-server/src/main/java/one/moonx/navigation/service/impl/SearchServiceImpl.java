package one.moonx.navigation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import one.moonx.navigation.mapper.SearchMapper;
import one.moonx.navigation.pojo.entity.Search;
import one.moonx.navigation.service.SearchService;
import org.springframework.stereotype.Service;

@Service
public class SearchServiceImpl extends ServiceImpl<SearchMapper, Search> implements SearchService {
}
