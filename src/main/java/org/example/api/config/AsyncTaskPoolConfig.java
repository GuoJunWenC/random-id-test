package org.example.api.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 异步任务池配置类
 *
 */
@Configuration
public class AsyncTaskPoolConfig {

    /**
     * 异步方法线程池
     *
     * @return Executor
     */
    @Bean(name = "asyncTaskThreadPool")
    public Executor asyncTaskThreadPool() {
        return new ThreadPoolExecutor(5, 10,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(2000), new ThreadFactoryBuilder()
                .setNameFormat("asyncTaskThreadPool-pool-%d").build(), new ThreadPoolExecutor.AbortPolicy());
    }
}
