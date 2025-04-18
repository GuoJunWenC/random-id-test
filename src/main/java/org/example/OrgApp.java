package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
/*
@EnableHystrix
*/
@EnableScheduling
@EnableAsync
@EnableAspectJAutoProxy
@SpringBootApplication
public class OrgApp {
    public static void main(String[] args) {
        SpringApplication.run(OrgApp.class,args);
     /*   SpringApplication application = new SpringApplication(OrgApp.class);
        application.addInitializers(new MyApplicationContextInitializer());
        application.run(args);*/
/*        MySpringApplication application = new MySpringApplication(OrgApp.class);
        application.run(args);*/
    }
}