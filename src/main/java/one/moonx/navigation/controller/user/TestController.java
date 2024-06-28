package one.moonx.navigation.controller.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/test")
public class TestController {
    @GetMapping("/api")
    public String api() {
        return "Hello World!";
    }
}
