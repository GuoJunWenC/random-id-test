package org.example.api.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class RedissonConfig implements BeanPostProcessor {
    private static final String REDIS_ADDRESS = "redis://8.142.153.195:6379";
    private static int i = 0;
    private static volatile RedissonClient redissonClient;

    public static RedissonClient getInstance() {
        if (redissonClient == null) {
            synchronized (RedissonConfig.class) {
                if (redissonClient == null) {
                    Config config = new Config();
                    config.useSingleServer().setAddress(REDIS_ADDRESS);
                    redissonClient = Redisson.create(config);
                    return redissonClient;
                }
            }
        }
        return redissonClient;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println(beanName + i++);
        return bean;
    }
}
