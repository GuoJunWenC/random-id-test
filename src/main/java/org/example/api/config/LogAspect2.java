package org.example.api.config;

import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
@Order(2)
@Component
public class LogAspect2 {
    @Pointcut("execution(* org.example.api.service.impl.UserServiceImpl.deleteUser(..))")
    private void controllerAspect() {
    }
   @Before("controllerAspect()")
    public void beforeUser() {
        System.out.println("Before2 Delete User...");
    }

    @SneakyThrows
    @Around("controllerAspect()")
    public Object aroundUser(ProceedingJoinPoint pjp) {
        System.out.println("Around2 before Delete User...");
        Object proceed = null;
        try {
            proceed = pjp.proceed();
            System.out.println("Around2 after Delete User...");
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }finally {
            System.out.println("Around2 finally Delete User...");
        }
        return proceed;
    }
    @After("controllerAspect()")
    public void afterUser() {
        System.out.println("After2 Delete User...");
    }

    @AfterThrowing("controllerAspect()")
    public void afterThrowingUser() {
        System.out.println("AfterThrowing2 Delete User...");
    }

    @AfterReturning("controllerAspect()")
    public void afterReturningUser() {
        System.out.println("AfterReturning2 Delete User...");
    }
}
