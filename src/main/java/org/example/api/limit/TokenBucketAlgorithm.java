package org.example.api.limit;

import com.google.common.util.concurrent.RateLimiter;

public class TokenBucketAlgorithm {
    private static final double RATE = 1.0;           // 令牌放入速率

    private RateLimiter rateLimiter;

    public TokenBucketAlgorithm() {
        this.rateLimiter = RateLimiter.create(RATE);
    }

    public void processRequest() {
        if (rateLimiter.tryAcquire()) {
            System.out.println("处理请求");

        } else {
            // 请求被限流
            System.out.println("请求被限流");
        }
        try {
            // 处理请求
            Thread.sleep(500);  // 模拟请求处理时间
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int requestCount = 15;      // 请求总数
        TokenBucketAlgorithm algorithm = new TokenBucketAlgorithm();
        // 模拟请求
        for (int i = 1; i <= requestCount; i++) {
            algorithm.processRequest();
        }
    }
}
