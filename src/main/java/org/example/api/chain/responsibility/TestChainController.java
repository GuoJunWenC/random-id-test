package org.example.api.chain.responsibility;

import org.apache.catalina.connector.Connector;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/chain")
public class TestChainController {
    @Autowired
    private ChainPatternDemo chainPatternDemo;
    @RequestMapping(value = "/test")
    public void testChain() {
        System.out.println(chainPatternDemo.getAbstractHandler());
        chainPatternDemo.exec(new Request(new Connector()), new Response());
    }
}
