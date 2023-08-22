package org.example.api.proxy;

/**
 * 被代理类
 */
public class SimpleProvider implements Provider {
    @Override
    public void printData(String str) {
        System.out.println(str);
    }
}
