package one.moonx.navigation.controller.admin;

import one.moonx.navigation.service.NavService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/nav")
public class NavController {
    @Autowired
    private NavService navService;
}
