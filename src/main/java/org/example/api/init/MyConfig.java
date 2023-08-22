package org.example.api.init;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {
    @Bean(initMethod = "init",destroyMethod = "destroy")
    public MyBean getBeanOne() {
        return new MyBean();
    }
}
