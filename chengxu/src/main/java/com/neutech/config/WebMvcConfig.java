package com.neutech.config;

import com.neutech.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    //spring回调用这个方法注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //addPathPatterns是添加拦截地址
        registry.addInterceptor(loginInterceptor).addPathPatterns(
                "/product/**","/category/**","/upload/**");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //addMapping项目下哪个地址处理跨域
        //allowedOrigins要写具体地址 不能用**
        registry.addMapping("/**").
                allowCredentials(true).
                allowedOrigins("http://localhost:9528");
    }
}
