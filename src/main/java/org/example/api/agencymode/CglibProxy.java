package org.example.api.agencymode;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxy implements MethodInterceptor {
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        preRequest();
        Object result = proxy.invokeSuper(obj, args);
        postRequest();
        return result;
    }

    private void preRequest() {
        System.out.println("CglibProxy: Pre-request processing.");
    }

    private void preRequest2() {
        System.out.println("CglibProxy: Pre-request processing.");
    }

    private void postRequest() {
        System.out.println("CglibProxy: Post-request processing.");
    }

    // 创建代理对象的方法
    public static RealSubject createProxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(RealSubject.class);
        enhancer.setCallback(new CglibProxy());
        return (RealSubject) enhancer.create();
    }
}
