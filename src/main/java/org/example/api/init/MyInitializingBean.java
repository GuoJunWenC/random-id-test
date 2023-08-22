package org.example.api.init;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class MyInitializingBean implements InitializingBean {
    @Override
    public void afterPropertiesSet() {
        System.out.println("afterPropertiesSet");
    }
}
