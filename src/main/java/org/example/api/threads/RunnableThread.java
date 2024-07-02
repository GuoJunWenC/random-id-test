package org.example.api.threads;

public class RunnableThread implements Runnable{
    @Override
    public void run() {

        System.out.println("线程启动了"+this.toString());
    }
}
