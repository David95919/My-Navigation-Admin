package one.moonx.navigation;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("one.moonx.navigation.mapper")
public class MyNavigationAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyNavigationAdminApplication.class, args);
    }

}
