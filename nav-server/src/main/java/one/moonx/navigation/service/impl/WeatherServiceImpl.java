package one.moonx.navigation.service.impl;

import lombok.extern.slf4j.Slf4j;
import one.moonx.navigation.constant.MessageConstant;
import one.moonx.navigation.exception.BaseException;
import one.moonx.navigation.pojo.dto.WeatherIdDTO;
import one.moonx.navigation.properties.WeatherProperties;
import one.moonx.navigation.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class WeatherServiceImpl implements WeatherService {
    @Autowired
    private WeatherProperties weatherProperties;
    @Autowired
    private RestTemplate restTemplate;

    private static final String cacheNames = "Weather";

    @Override
    @Cacheable(cacheNames = cacheNames + "Id", key = "#city+'_'+#area")
    public String getWeatherId(String city, String area) {
        String url = "https://geoapi.qweather.com/v2/city/lookup?key={key}&adm={city}&location={area}";
        ResponseEntity<WeatherIdDTO> result = restTemplate.getForEntity(url, WeatherIdDTO.class, weatherProperties.getKey(), city, area);

        if (result.getBody().code != 200) {
            throw new BaseException(MessageConstant.WEATHER_SERVICE_ERROR);
        }

        if (result.getBody().getLocation().size() >= 1) {
            log.info("获取 天气id:{}", result.getBody().getLocation());
            return result.getBody().getLocation().get(0).id;
        } else {
            throw new BaseException(MessageConstant.UNKNOWN_ERROR);
        }
    }
}
