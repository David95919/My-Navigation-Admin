package one.moonx.navigation.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "one.moonx.jwt")
@Data
public class JwtProperties {
    private String adminSecretKey;
    private long adminTtl;
    private String adminTokenName;
}
