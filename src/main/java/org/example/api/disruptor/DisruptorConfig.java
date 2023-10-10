package org.example.api.disruptor;

import org.example.api.config.RedissonConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DisruptorConfig {
    private final static Logger logger = LoggerFactory.getLogger(DisruptorConfig.class);

    private final static String LIST_KEY = "disruptor:list";

    @Bean
    public DataEventListener<String> createConsumerListener() {
        DataEventListener<String> dataEventListener = dataEvent -> {
            logger.info("processDateEvent data:" + dataEvent.getData());
            RedissonConfig.getInstance().getList(LIST_KEY).add(dataEvent.getData());
        };
        return dataEventListener;
    }

    @Bean
    public DisruptorProducer<String> createProducer(DataEventListener dataEventListener) {
        DisruptorManager disruptorManage = new DisruptorManager(dataEventListener,
                8,
                1024 * 1024);
        disruptorManage.start();
        return disruptorManage.getProducer();
    }
}
