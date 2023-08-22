package org.example.api.config;

import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
@Order(1)
@Component

public class LogAspect1 {
    @Pointcut("execution(* org.example.api.service.impl.UserServiceImpl.deleteUser(..))")
    private void controllerAspect() {
    }
   @Before("controllerAspect()")
    public void beforeUser() {
        System.out.println("Before1 Delete User...");
    }

    @SneakyThrows
    @Around("controllerAspect()")
    public Object aroundUser(ProceedingJoinPoint pjp) {
        System.out.println("Around1 before Delete User...");
        Object proceed = null;
        try {
            proceed = pjp.proceed();
            System.out.println("Around1 after Delete User...");
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }finally {
            System.out.println("Around1 finally Delete User...");
        }
        return proceed;
    }
    @After("controllerAspect()")
    public void afterUser() {
        System.out.println("After1 Delete User...");
    }

    @AfterThrowing("controllerAspect()")
    public void afterThrowingUser() {
        System.out.println("AfterThrowing1 Delete User...");
    }

    @AfterReturning("controllerAspect()")
    public void afterReturningUser() {
        System.out.println("AfterReturning1 Delete User...");
    }
}
