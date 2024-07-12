package com.app.config;

import com.app.filter.XSSFilter;
import jakarta.servlet.FilterRegistration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

public class WebConfig {

    @Bean
    public FilterRegistrationBean<XSSFilter> filterRegistrationBean()
    {
        // Create  a new FilterRegistrationBean for XSSFilter
        FilterRegistrationBean<XSSFilter>  registrationBean = new FilterRegistrationBean<>();

        // Set XSSFilter instance as the filter to be registered
        registrationBean.setFilter(new XSSFilter());

        //Spcifing the URL patterns on which it will be applied
        registrationBean.addUrlPatterns("/*");

        //Setting the order
        registrationBean.setOrder(1);

        // Return the configured FilterRegistrationBean
        return registrationBean;
    }
}
