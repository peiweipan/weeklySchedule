package com.weekly.filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class FilterConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenFilter()).addPathPatterns("/user/**");
        registry.addInterceptor(tokenFilter()).addPathPatterns("/task/**");
        registry.addInterceptor(tokenFilter()).addPathPatterns("/summary/**");
        registry.addInterceptor(tokenFilter()).addPathPatterns("/semester/**");

    }

    @Bean
    public TokenFilter tokenFilter(){return new TokenFilter();}
}