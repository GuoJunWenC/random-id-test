package org.example.api.chain.responsibility;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)//校验顺序排第3
public class CheckBlackFilterObject extends AbstractHandler{
    @Override
    void doFilter(Request filterRequest, Response response) {
          //invoke black list check
        System.out.println("校验黑名单");
    }
}
