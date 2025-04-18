package org.example.api.agencymode;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * InvocationHandler 实现类
 */
public class ProxyHandler implements InvocationHandler {
    private final Object target;

    public ProxyHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        preRequest();
        Object result = method.invoke(target, args);
        postRequest();
        return result;
    }

    public static void main(String[] args) {
        System.out.println("20244".substring(0,4));
    }
    private void preRequest() {
        System.out.println("ProxySubject: Preparing for request.");
    }

    private void postRequest() {
        System.out.println("ProxySubject: Finishing up request.");
    }
}
