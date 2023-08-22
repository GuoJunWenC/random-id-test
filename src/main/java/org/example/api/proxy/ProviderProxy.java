package org.example.api.proxy;

/**
 * 代理类
 */
public class ProviderProxy implements Provider{
    //持有一个被代理对象的引用（在这里是SimpleProvider）
    private final Provider provider;

    public ProviderProxy(Provider provider){
        this.provider = provider;
    }

    @Override
    public void printData(String str) {
        System.out.println("开始");

        provider.printData(str);

        System.out.println("结束");
    }
}
