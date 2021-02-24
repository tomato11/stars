package com.design.pension_system.sys.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfigurer implements WebMvcConfigurer {

    @Autowired
    private UserInterceptor userInterceptor;
    //添加拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(userInterceptor)
                .addPathPatterns("/**").excludePathPatterns("/api/sendLoginCode/**","/api/register/**","/api/loginCheck/**");
    }
}