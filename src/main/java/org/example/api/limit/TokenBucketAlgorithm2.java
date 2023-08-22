package org.example.api.limit;

import lombok.SneakyThrows;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 令牌桶算法
 */
public class TokenBucketAlgorithm2 {
    private final int capacity;                       // 令牌桶容量
    private final Semaphore semaphore;                // 信号量，用于控制令牌的获取

    public TokenBucketAlgorithm2(int capacity) {
        this.capacity = capacity;
        this.semaphore = new Semaphore(capacity);
    }

    /**
     * 处理请求
     */
    @SneakyThrows
    public void processRequest() {
        if (semaphore.tryAcquire()) {
            // 处理请求
            System.out.println("处理请求，剩余令牌数：" + semaphore.availablePermits());
        } else {
            // 请求被限流
            System.out.println("请求被限流");
        }
        TimeUnit.MILLISECONDS.sleep(500);  // 模拟请求处理时间

    }

    /**
     * 添加令牌
     */
    public void releaseToken() {
        if (semaphore.availablePermits() < capacity) {
            semaphore.release(20);  // 往令牌桶放入一个令牌
        }
    }

    public static void main(String[] args) {
        int capacity = 10;          // 令牌桶容量
        TokenBucketAlgorithm2 algorithm = new TokenBucketAlgorithm2(capacity);
        // 开启一个后台线程每隔一段时间往令牌桶中放入一个令牌
        Thread tokenProducer = new Thread(() -> {
            while (true) {
                algorithm.releaseToken();
                try {
                    TimeUnit.SECONDS.sleep(1);  // 每隔1秒放入一个令牌
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        });
        tokenProducer.setDaemon(true);  // 设置为守护线程
        tokenProducer.start();

        // 模拟请求
        for (int i = 1; i <= 50; i++) {
            algorithm.processRequest();
        }
    }
}
