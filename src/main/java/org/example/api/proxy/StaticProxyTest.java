package org.example.api.proxy;

public class StaticProxyTest {
    public static void main(String[] args) {
        Cat cat = new Cat();
        CglibProxy cglibProxy = new CglibProxy();
        Animal proxy = (Animal)cglibProxy.getProxy(cat);
        proxy.add();
    }
}
