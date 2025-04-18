package org.example.api.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;


@Slf4j
@Configuration
@EnableAsync
public class SpringAsyncConfigurer extends AsyncConfigurerSupport {

    @Bean
    public ThreadPoolTaskExecutor asyncExecutor() {
        ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
        threadPool.setCorePoolSize(3);
        threadPool.setMaxPoolSize(3);
        threadPool.setWaitForTasksToCompleteOnShutdown(true);
        threadPool.setAwaitTerminationSeconds(60 * 15);
        threadPool.setKeepAliveSeconds(0);
        threadPool.setThreadNamePrefix("SpringAsyncConfigurer");
        threadPool.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        return threadPool;
    }

    @Override
    public Executor getAsyncExecutor() {
        return asyncExecutor();
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (arg0, arg1, arg2) -> {
            log.error("==========================" + arg0.getMessage() + "=======================", arg0);
            log.error("exception method:" + arg1.getName());
            System.out.println(Arrays.stream(arg2).map(Object::toString).collect(Collectors.toList()));
        };
    }
}
