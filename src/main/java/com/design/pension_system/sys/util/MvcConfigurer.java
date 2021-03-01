package com.design.pension_system.sys.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfigurer implements WebMvcConfigurer {

    @Autowired
    private UserInterceptor userInterceptor;



    //添加拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(userInterceptor)
                .addPathPatterns("/**").excludePathPatterns(
                "/api/sendLoginCode/**","/api/register/**","/api/loginCheck/**"
                ,"/api/selectCommonDic/**" ,"/api/selectCommonDic/multi/**",
                "/api/user/getBusinessByType/**",
                "/api/xzqh/tree/**", "/api/checkPhone/**","/api/checkLoginId/**", "/upload/**","/api/upload/**"
        ,"/api/checkLoginId/**");
    }
//    ,"/*.html"
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        //需要配置1：----------- 需要告知系统，这是要被当成静态文件的！
//        //第一个方法设置访问路径前缀，第二个方法设置资源路径
//        registry.addResourceHandler("/*.html").addResourceLocations("classpath:/static/");
//    }



}
