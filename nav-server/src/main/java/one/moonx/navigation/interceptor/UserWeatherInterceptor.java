package one.moonx.navigation.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import one.moonx.navigation.properties.WeatherProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 用户天气拦截器
 *
 * @author Inepts
 * @date 2024/07/26
 */
@Component
@Slf4j
public class UserWeatherInterceptor implements HandlerInterceptor {
    @Autowired
    private WeatherProperties weatherProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断当前拦截到的是Controller的方法还是其他资源
        if (!(handler instanceof HandlerMethod)) {
            //当前拦截到的不是动态方法，直接放行
            return true;
        }

        if (weatherProperties.getEnable()) {
            return true;
        } else {
            log.warn("未开启天气api");
            response.setStatus(503);
            return false;
        }
    }
}
