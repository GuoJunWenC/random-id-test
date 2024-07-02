package org.example.api.threads;

public class ExtendsThread extends Thread {
    @Override
    public void run() {
        System.out.println("用Thread类实现线程");
    }
}