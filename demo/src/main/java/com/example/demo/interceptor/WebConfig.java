package com.example.demo.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * 自定义拦截器，添加拦截路径和排除拦截路径
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Resource
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器
        LoginInterceptor loginInterceptor = new LoginInterceptor();
        InterceptorRegistration loginRegistry = registry.addInterceptor(loginInterceptor);
        // 拦截路径
        loginRegistry.addPathPatterns("/hkexpress/**");
        // 排除路径
        loginRegistry.excludePathPatterns("/");
        // 排除登陆
        loginRegistry.excludePathPatterns("/hkexpress/admin/login");
        // 排除客户前端
        loginRegistry.excludePathPatterns("/hkexpress/index");
        loginRegistry.excludePathPatterns("/hkexpress/indexDetailed");
        loginRegistry.excludePathPatterns("/hkexpress/search");

        // 排除资源请求
        loginRegistry.excludePathPatterns("/css/**");
        loginRegistry.excludePathPatterns("/js/**");
        loginRegistry.excludePathPatterns("/img/**");
    }

}
