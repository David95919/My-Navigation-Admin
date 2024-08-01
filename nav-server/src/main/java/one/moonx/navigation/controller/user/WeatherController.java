package one.moonx.navigation.controller.user;

import one.moonx.navigation.base.Result;
import one.moonx.navigation.constant.MessageConstant;
import one.moonx.navigation.pojo.dto.WeatherNowDTO;
import one.moonx.navigation.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("userWeatherController")
@RequestMapping("/user/weather")
public class WeatherController {
    @Autowired
    private WeatherService weatherService;

    /**
     * 获取天气 ID
     *
     * @param city 城市
     * @param area 地区
     * @return {@link Result }<{@link String }>
     */
    @GetMapping("/id")
    public Result<String> getWeatherId(String city, String area) {
        String id = weatherService.getWeatherId(city, area);

        return Result.success.msgAndData(MessageConstant.GET_SUCCESS, id);
    }

    /**
     * 获取天气
     *
     * @param weatherId 天气 ID
     * @return {@link Result }<{@link WeatherNowDTO }>
     */
    @GetMapping
    public Result<WeatherNowDTO> getWeather(String weatherId) {
        WeatherNowDTO result = weatherService.getWeather(weatherId);

        return Result.success.msgAndData(MessageConstant.GET_SUCCESS, result);
    }
}
