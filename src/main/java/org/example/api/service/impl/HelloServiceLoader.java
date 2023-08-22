package org.example.api.service.impl;

import org.example.api.service.HelloService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.support.SpringFactoriesLoader;

import java.util.List;
import java.util.ServiceLoader;

public class HelloServiceLoader {
    public static void main(String[] args) {
        // jdk原生api 使用SPI机制动态加载HelloService实现类
        ServiceLoader<HelloService> loader = ServiceLoader.load(HelloService.class);
        // 遍历所有实现类并调用sayHello方法
        for (HelloService helloService : loader) {
            helloService.sayHello();
        }
    }
/*    public static void main(String[] args) {
        // 使用Spring SPI机制动态加载HelloService实现类
        List<HelloService> loader = SpringFactoriesLoader.loadFactories(HelloService.class,Thread.currentThread().getContextClassLoader());
        // 遍历所有实现类并调用sayHello方法
        for (HelloService helloService : loader) {
            helloService.sayHello();
        }

    }*/
}
