package one.moonx.navigation.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "one.moonx.weather")
@Data
public class WeatherProperties {
    private Boolean enable;
    private String key;
}
