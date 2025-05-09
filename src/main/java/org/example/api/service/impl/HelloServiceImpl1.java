package org.example.api.service.impl;

import org.example.api.service.HelloService;

public class HelloServiceImpl1 implements HelloService {
    @Override
    public void sayHello() {
        System.out.println("Hello World from HelloServiceImpl1!");
        System.out.println("Hello World from HelloServiceImpl1!");
    }

    @Override
    public void sayHelloTest() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sayHelloTest'");
    }
}
