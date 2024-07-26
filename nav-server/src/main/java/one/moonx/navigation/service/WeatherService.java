package one.moonx.navigation.service;

import one.moonx.navigation.pojo.dto.WeatherNowDTO;

public interface WeatherService {
    String getWeatherId(String city, String area);

    WeatherNowDTO getWeather(String weatherId);
}
