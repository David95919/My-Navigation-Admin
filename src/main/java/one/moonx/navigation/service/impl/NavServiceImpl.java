package one.moonx.navigation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import one.moonx.navigation.pojo.entity.Nav;
import one.moonx.navigation.mapper.NavMapper;
import one.moonx.navigation.service.NavService;
import org.springframework.stereotype.Service;

@Service
public class NavServiceImpl extends ServiceImpl<NavMapper, Nav> implements NavService {
}
