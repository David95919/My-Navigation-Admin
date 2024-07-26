package one.moonx.navigation.controller.user;

import one.moonx.navigation.base.Result;
import one.moonx.navigation.constant.MessageConstant;
import one.moonx.navigation.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/weather")
public class WeatherController {
    @Autowired
    private WeatherService weatherService;

    @GetMapping("/id")
    public Result<String> getWeatherId(String city, String area) {
        String id = weatherService.getWeatherId(city, area);
        return Result.success.msgAndData(MessageConstant.GET_SUCCESS, id);
    }
}
