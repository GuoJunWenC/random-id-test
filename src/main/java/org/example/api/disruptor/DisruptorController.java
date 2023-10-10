package org.example.api.disruptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/disruptor")
public class DisruptorController {
    @Autowired
    private DisruptorProducer<String> producer;

    @RequestMapping("/test")
    public void test(String log) {
        producer.onData(log);

    }
}
