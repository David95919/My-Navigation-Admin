package one.moonx.navigation.config;

import lombok.extern.slf4j.Slf4j;
import one.moonx.navigation.interceptor.JwtTokenAdminInterceptor;
import one.moonx.navigation.interceptor.UserWeatherInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 配置类，注册web层相关组件
 */
@Configuration
@Slf4j
public class WebMvcConfiguration extends WebMvcConfigurationSupport {
    @Autowired
    private JwtTokenAdminInterceptor jwtTokenAdminInterceptor;
    @Autowired
    private UserWeatherInterceptor userWeatherInterceptor;


    /**
     * 添加拦截器
     *
     * @param registry 注册表
     */
    protected void addInterceptors(InterceptorRegistry registry) {
        log.info("开始注册自定义拦截器...");
        registry.addInterceptor(jwtTokenAdminInterceptor)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/user/login");

        registry.addInterceptor(userWeatherInterceptor)
                .addPathPatterns("/user/weather/**");
    }
}
