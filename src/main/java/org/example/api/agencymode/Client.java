package org.example.api.agencymode;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * 客户端代码
 */
public class Client {
    /* public static void main(String[] args) {
         Subject subject = new ProxySubject();
         subject.request();
     }*/
    /*public static void main(String[] args) {
        RealSubject realSubject = new RealSubject();
        InvocationHandler handler = new ProxyHandler(realSubject);
        //测试
        Subject subject = (Subject) Proxy.newProxyInstance(
                realSubject.getClass().getClassLoader(),
                realSubject.getClass().getInterfaces(),
                handler
        );
        subject.request();
    }*/

    public static void main(String[] args) {
        RealSubject proxySubject = CglibProxy.createProxy();
        proxySubject.request();
    }
}
