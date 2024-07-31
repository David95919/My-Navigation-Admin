package one.moonx.navigation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import one.moonx.navigation.mapper.SearchCategoryMapper;
import one.moonx.navigation.pojo.entity.SearchCategory;
import one.moonx.navigation.service.SearchCategoryService;
import org.springframework.stereotype.Service;

@Service
public class SearchCategoryServiceImpl extends ServiceImpl<SearchCategoryMapper, SearchCategory> implements SearchCategoryService {
}
