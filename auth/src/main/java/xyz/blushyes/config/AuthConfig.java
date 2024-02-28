package xyz.blushyes.config;

import static xyz.blushyes.constant.Const.TOKEN_HEADER;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.jwt.StpLogicJwtForStateless;
import cn.dev33.satoken.stp.StpLogic;
import cn.dev33.satoken.stp.StpUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class AuthConfig implements WebMvcConfigurer {
    @Bean
    public StpLogic getStpLogicJwt() {
        return new StpLogicJwtForStateless();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                if (request.getMethod().equals(RequestMethod.OPTIONS.name())) {
                    return true;
                }
                StpUtil.setTokenValue(request.getHeader(TOKEN_HEADER));
                return true;
            }
        }).addPathPatterns("/**").order(1);


        // 注册 Sa-Token 拦截器，校验规则为 StpUtil.checkLogin() 登录校验
        registry.addInterceptor(new SaInterceptor(handle -> StpUtil.checkLogin()))
                .addPathPatterns("/**")
                // 排除 auth 相关请求
                .excludePathPatterns("/auth/**")
                // 排除 swagger 相关请求
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/*/api-docs/**", "/doc.html/**")
                .order(2);
    }
}
