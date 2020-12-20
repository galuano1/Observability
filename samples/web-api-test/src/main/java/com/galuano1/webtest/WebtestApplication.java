package com.galuano1.webtest;

import com.galuano1.zipkin.filter.TracingFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WebtestApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebtestApplication.class, args);
    }

    /**
     * Register the TracingFilter
     */
    @Bean
    public FilterRegistrationBean<TracingFilter> tracingFilter() {
        FilterRegistrationBean<TracingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new TracingFilter());

        return registrationBean;
    }

}
