package com.tenpo.challenge.application.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class EndpointMVCConfig implements WebMvcConfigurer {

    private final EndpointRequestInterceptor endpointRequestInterceptor;

    public EndpointMVCConfig(EndpointRequestInterceptor endpointRequestInterceptor) {
        this.endpointRequestInterceptor = endpointRequestInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(endpointRequestInterceptor);
    }
}