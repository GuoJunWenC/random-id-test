package org.example.api.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

public class GuavaCacheTest {
    private final Cache<String, Object> cache;

    public GuavaCacheTest() {
        cache = CacheBuilder.newBuilder()
                .maximumSize(100) // 设置缓存的最大容量
                .expireAfterWrite(10, TimeUnit.MINUTES) // 设置缓存的过期时间，这里表示缓存中的数据在写入10分钟后自动过期
                .build();
    }

    /**
     * 获取缓存
     *
     * @param key
     * @return
     */
    public Object get(String key) {
        return cache.getIfPresent(key);
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     */
    public void put(String key, Object value) {
        cache.put(key, value);
    }

    /**
     * 删除缓存
     *
     * @param key
     */
    public void invalidate(String key) {
        cache.invalidate(key);
    }
}
