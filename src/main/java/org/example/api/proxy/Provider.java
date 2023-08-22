package org.example.api.proxy;

/**
 * 静态代理举例
 * 特点：代理类和被代理类在编译期间，就确定下来了。
 */
public interface Provider {
    void printData(String str);
}
