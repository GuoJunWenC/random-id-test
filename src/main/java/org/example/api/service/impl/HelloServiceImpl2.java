package org.example.api.service.impl;

import org.example.api.service.HelloService;
import org.example.api.service.User;

public class HelloServiceImpl2 implements HelloService {
    @Override
    public void sayHello() {
        System.out.println("Hello World from HelloServiceImpl2!");
    }

    @Override
    public void sayHelloTest() {
        new Thread(() -> {
            System.out.println("Hello World from HelloServiceImpl2!");
            // TODO Auto-generated method stub

        }).start();
        User sUser = new User();
        throw new UnsupportedOperationException("Unimplemented method 'sayHelloTest'");
    }
}
