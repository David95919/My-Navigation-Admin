package one.moonx.navigation.interceptor;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import one.moonx.navigation.base.BaseContext;
import one.moonx.navigation.constant.JwtClaimsConstant;
import one.moonx.navigation.properties.JwtProperties;
import one.moonx.navigation.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * JWT 令牌管理拦截器
 *
 * @author Inepts
 * @date 2024/06/29
 */
@Component
@Slf4j
public class JwtTokenAdminInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 预处理
     *
     * @param request  请求
     * @param response 响应
     * @param handler  处理器
     * @return boolean 是否放行
     * @throws Exception 例外
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断当前拦截到的是Controller的方法还是其他资源
        if (!(handler instanceof HandlerMethod)) {
            //当前拦截到的不是动态方法，直接放行
            return true;
        }

        //从请求头中获取令牌
        String token = request.getHeader(jwtProperties.getAdminTokenName());

        //校验令牌
        try {
            Claims claims = JwtUtil.parseJWT(jwtProperties.getAdminSecretKey(), token);
            Long empId = Long.valueOf(claims.get(JwtClaimsConstant.USER_ID).toString());

            //把id存入线程存储
            BaseContext.setCurrentId(empId);

            //放行
            return true;
        } catch (Exception ex) {
            //不通过 响应401状态码
            response.setStatus(401);
            return false;
        }
    }
}
