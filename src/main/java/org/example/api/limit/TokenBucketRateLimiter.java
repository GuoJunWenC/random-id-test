package org.example.api.limit;

import org.example.api.config.RedissonConfig;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;

public class TokenBucketRateLimiter {

    public static final String KEY = "TokenBucketRateLimiter:";

    /**
     * 阈值
     */
    private final Long limit;
    /**
     * 添加令牌的速率，单位：个/秒
     */
    private final Long tokenRate;

    public TokenBucketRateLimiter(Long limit, Long tokenRate) {
        this.limit = limit;
        this.tokenRate = tokenRate;
    }

    /**
     * 限流算法
     */
    public boolean triggerLimit(String path){
        RedissonClient redissonClient= RedissonConfig.getInstance();
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(KEY+path);
        // 初始化，设置速率模式，速率，间隔，间隔单位
        rateLimiter.trySetRate(RateType.OVERALL, limit, tokenRate, RateIntervalUnit.SECONDS);
        // 获取令牌
        return rateLimiter.tryAcquire();
    }
}
