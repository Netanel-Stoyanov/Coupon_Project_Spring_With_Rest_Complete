package com.nikitacouponproject.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfigBean {

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public FilterRegistrationBean<LoginFilter> createFilter(){
        FilterRegistrationBean<LoginFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginFilter(applicationContext));
        filterRegistrationBean.addUrlPatterns("/admin/*", "/company/*","/customer/*");
        filterRegistrationBean.setOrder(1);
        return filterRegistrationBean;
    }
}
