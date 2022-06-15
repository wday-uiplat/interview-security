package com.wday;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.wday.security.SecurityInterceptor;

@Configuration
class InterviewSecurityWebMvcConfiguration
        implements WebMvcConfigurer {

    private final SecurityInterceptor securityInterceptor;

    public InterviewSecurityWebMvcConfiguration(SecurityInterceptor securityInterceptor) {
        this.securityInterceptor = securityInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(securityInterceptor)
                .addPathPatterns("/cards/credit");
    }
}
