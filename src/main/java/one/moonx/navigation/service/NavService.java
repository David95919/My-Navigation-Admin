package one.moonx.navigation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import one.moonx.navigation.pojo.dto.NavDTO;
import one.moonx.navigation.pojo.entity.Nav;

public interface NavService extends IService<Nav> {

   public void createNav(NavDTO navDTO);

    public  void updateNav(NavDTO navDTO);

}
