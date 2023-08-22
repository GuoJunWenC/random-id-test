package org.example.api.config;

import com.fasterxml.jackson.core.JsonParser;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.SneakyThrows;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.*;
@Service
public class TestB {
    @Async()
    @SneakyThrows
    public void testB() {
        TimeUnit.SECONDS.sleep(10);
        System.out.println("结束"+Thread.currentThread().getName());
    }

    /**
     * 异常调用返回Future
     * 对于返回值是Future，不会被AsyncUncaughtExceptionHandler处理，需要我们在方法中捕获异常并处理
     * 或者在调用方在调用Futrue.get时捕获异常进行处理
     *
     * @return Future<String>
     */
    @Async
    public Future<String> testC() {
        System.out.println("开始" + LocalDateTime.now());
        Future<String> future;
        try {
            TimeUnit.SECONDS.sleep(5);
            future = new AsyncResult<>("success");
        } catch (Exception e) {
            future = new AsyncResult<>("Exception" + e);
        }
        System.out.println("结束" + LocalDateTime.now());
        return future;
    }

    public ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(2000), new ThreadFactoryBuilder()
            .setNameFormat("asyncTaskThreadPool-pool-%d").build(), new ThreadPoolExecutor.AbortPolicy());

    @SneakyThrows
    public void TestD() {
        CompletableFuture<Integer> countFutureOne = CompletableFuture
                .supplyAsync(this::count, executor);
        CompletableFuture.allOf(countFutureOne).join();
        CompletableFuture<Integer> countFutureTwo = CompletableFuture
                .supplyAsync(this::count, executor);
        /**
         * CompletableFuture可以指定异步处理流程：
         *
         * thenAccept()处理正常结果；
         *
         * exceptional()处理异常结果；
         *
         * thenApplyAsync()用于串行化另一个CompletableFuture
         *
         * anyOf()和allOf()用于并行化多个CompletableFuture
         */
        CompletableFuture.allOf(countFutureTwo, countFutureOne).join();
        System.out.println("结束One" + countFutureOne.get());
        System.out.println("结束Two" + countFutureTwo.get());
    }

    @SneakyThrows
    public Integer count() {
        TimeUnit.SECONDS.sleep(5);
        System.out.println("调用" + LocalDateTime.now());
        return 1;
    }

}
