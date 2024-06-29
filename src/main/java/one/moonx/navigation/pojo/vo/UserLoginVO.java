package one.moonx.navigation.pojo.vo;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserLoginVO {
    private Integer id;

    private String username;

    private String token;
}
