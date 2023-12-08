package org.example.api.cache;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

public class EnCacheTest {
    /**
     * 缓存的使用
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // 声明一个cacheBuilder
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                    .withCache("ehcacheInstance", CacheConfigurationBuilder
                        //声明一个容量为20的堆内缓存
                        .newCacheConfigurationBuilder(String.class,String.class, ResourcePoolsBuilder.heap(2)))
                .build(true);
        // 获取Cache实例
        Cache<String,String> myCache =  cacheManager.getCache("ehcacheInstance", String.class, String.class);
        // 写缓存
        myCache.put("key","v");
        myCache.put("key1","v1");
        // 读缓存
        String value = myCache.get("key");
        System.out.println(value);
        // 移除缓存
        cacheManager.removeCache("myCache");
        cacheManager.close();
    }
}
