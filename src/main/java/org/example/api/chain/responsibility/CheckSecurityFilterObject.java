package org.example.api.chain.responsibility;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)//顺序排第1，最先校验
public class CheckSecurityFilterObject extends AbstractHandler {
    @Override
    void doFilter(Request filterRequest, Response response) {
        System.out.println("安全调用校验");
    }
}
