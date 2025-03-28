package com.example.sample.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoggerInterceptor())
                .addPathPatterns("/view/main/**")
                .excludePathPatterns("/css/**","/js/**");

        registry.addInterceptor(new AuthCheckInterceptor())
                .addPathPatterns("/edit/**")
                .addPathPatterns("/user/**")
                .addPathPatterns("/question/questionDetail");
    }

}
