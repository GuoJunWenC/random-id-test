package org.example.api.init;

import org.springframework.stereotype.Component;

@Component
public class InitTestService {
    public InitTestService(){
        System.out.println("构造函数");
    }

}
