package org.example.api.init;

public class MyBean {
    public void init() {
        System.out.println("Bean初始化完成，调用...........");
    }

    public void destroy() {
        System.out.println("Bean销毁，调用...........");
    }
}
