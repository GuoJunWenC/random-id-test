package org.example.api.agencymode;

/**
 * 代理主题
 */
public class ProxySubject implements Subject {
    private RealSubject realSubject;

    @Override
    public void request() {
        if (realSubject == null) {
            realSubject = new RealSubject();
        }

        preRequest();
        realSubject.request();
        postRequest();
    }

    private void preRequest() {
        System.out.println("ProxySubject: Preparing for request.");
    }

    private void postRequest() {
        System.out.println("ProxySubject: Finishing up request.");
    }
}
