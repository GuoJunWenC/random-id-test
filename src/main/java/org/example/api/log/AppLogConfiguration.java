package org.example.api.log;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppLogConfiguration {

    @Bean
    public WebMvcConfigurer appLogWebMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                // 添加拦截器
                registry.addInterceptor(new AppLogInterceptor());
                // 添加拦截器
            }
        };
    }
}
