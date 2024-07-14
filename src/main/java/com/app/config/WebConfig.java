package com.app.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.app.filter.XSSFilter;

@Configuration
public class WebConfig {


    private Logger logger = LoggerFactory.getLogger(WebConfig.class);

    // Registers the custom filter (XSSFilter) and specifies its configuration.
    @Bean
    public FilterRegistrationBean<XSSFilter> filterRegistrationBean()
    {
        logger.info("In the WebConfig");

        // Create  a new FilterRegistrationBean for XSSFilter
        FilterRegistrationBean<XSSFilter>  registrationBean = new FilterRegistrationBean<>();

        // Set XSSFilter instance as the filter to be registered
        registrationBean.setFilter(new XSSFilter());

        //Spcifing the URL patterns on which it will be applied
        registrationBean.addUrlPatterns("/*");

        // Setting the order
        registrationBean.setOrder(1);

        // Return the configured FilterRegistrationBean
        return registrationBean;
    }
        
}
