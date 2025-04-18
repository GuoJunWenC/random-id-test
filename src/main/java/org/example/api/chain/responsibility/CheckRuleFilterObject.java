package org.example.api.chain.responsibility;

/**
 * 规则拦截对象
 */
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(4) //校验顺序排第4
public class CheckRuleFilterObject extends AbstractHandler {

    @Override
    public void doFilter(Request request, Response response) {
        //check rule
        System.out.println("check rule");
    }
}