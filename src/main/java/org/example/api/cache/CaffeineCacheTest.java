package org.example.api.cache;

import com.github.benmanes.caffeine.cache.*;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import com.google.common.collect.Lists;
import com.google.common.graph.Graph;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.example.api.log.AppLog;
import org.example.api.proxy.Cat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Caching;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Slf4j
public class CaffeineCacheTest {
  /*  public static Cache<String, String> LOADING_CACHE = Caffeine.newBuilder()
            //cache的初始容量
            .initialCapacity(5)
            //cache最大缓存数
            .maximumSize(10)
            //设置写缓存后n秒钟过期
            .expireAfterWrite(17, TimeUnit.SECONDS)
            //设置读写缓存后n秒钟过期,实际很少用到,类似于expireAfterWrite
            //.expireAfterAccess(17, TimeUnit.SECONDS)
            .build();

    public static void main(String[] args) throws Exception {
        //创建guava cache
        String key = "key";
        // 往缓存写数据
        LOADING_CACHE.put(key, "value");
        // 获取value的值，如果缓存不存在则生成缓存元素,  如果无法生成则返回null
        String value = LOADING_CACHE.get("key1", CaffeineCacheTest::getValueFromDB);
        System.out.println(value);
        // 获取value的值，如果key不存在，立即返回null
        String ifPresent = LOADING_CACHE.getIfPresent("key1");
        System.out.println(ifPresent);
        // 移除一个缓存元素
        LOADING_CACHE.invalidate(key);
    }

   */

   /* public static AsyncLoadingCache<String, String> cache = Caffeine.newBuilder()
            .maximumSize(10_000)
            .expireAfterWrite(10, TimeUnit.MINUTES)
            //默认情况下，缓存计算使用ForkJoinPool.commonPool()作为线程池，如果想要指定线程池，则可以覆盖并实现Caffeine.executor(Executor)方法
            .executor(new ThreadPoolExecutor(5, 10,
                    0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<>(2000), new ThreadFactoryBuilder()
                    .setNameFormat("asyncTaskThreadPool-pool-%d").build(), new ThreadPoolExecutor.AbortPolicy()))
            // 构建一个异步缓存元素操作并返回一个future
            .buildAsync(CaffeineCacheTest::createExpensiveStringAsync);

    private static CompletableFuture<String> createExpensiveStringAsync(String key, Executor executor) {
        System.out.println(executor.toString());
        return CompletableFuture.supplyAsync(() -> getValueFromDB(key), executor);
    }

    public static void main(String[] args) throws Exception {
        // 查找缓存元素，如果其不存在，将会异步进行生成
        CompletableFuture<String> future = cache.get("key");
        future.thenAccept(System.out::println);
        // 批量查找缓存元素，如果其不存在，将会异步进行生成
        List<String> keys = Lists.newArrayList("key1", "key2", "key3");
        CompletableFuture<Map<String, String>> graphs = cache.getAll(keys);
        graphs.thenAccept(System.out::println);
    }


    }*/
    // Evict based on the number of entries in the cache
   /*public static void main(String[] args) throws InterruptedException {
       // 根据缓存的计数进行驱逐
       Cache<Integer, Integer> cache = Caffeine.newBuilder()
               //超过10个后会使用W-TinyLFU算法进行淘汰
               .maximumSize(10)
               .removalListener((key, val, removalCause) -> log.info("淘汰缓存：key:{} val:{},removalCause={}", key, val,removalCause))
               .build();
       for (int i = 1; i < 20; i++) {
           cache.put(i, i);
       }
       Thread.sleep(500);//缓存淘汰是异步的
       // 打印还没被淘汰的缓存
       System.out.println(cache.asMap());
   }*/
    /*// 基于缓存内元素权重进行驱逐
    public static void main(String[] args) throws InterruptedException {
       Cache<Integer, Integer> cache = Caffeine.newBuilder()
               //限制总权重，若所有缓存的权重加起来>总权重 就会淘汰权重小的缓存
               .maximumWeight(100)
               .weigher((Weigher<Integer, Integer>) (key, value) -> key)
               .removalListener((key, val, removalCause) -> {
                   log.info("淘汰缓存：key:{} val:{}", key, val);
               })
               .build();

       //总权重其实是=所有缓存的权重加起来
       int maximumWeight = 0;
       for (int i = 1; i < 20; i++) {
           cache.put(i, i);
           maximumWeight += i;
       }
       System.out.println("总权重=" + maximumWeight);
       Thread.sleep(500);//缓存淘汰是异步的

       // 打印还没被淘汰的缓存
       System.out.println(cache.asMap());
   }
*/

    /* *//**
     * 访问后到期（每次访问都会重置时间，也就是说如果一直被访问就不会被淘汰）
     *//*
    public static void main(String[] args) throws InterruptedException {
        Cache<Integer, Integer> cache = Caffeine.newBuilder()
                .expireAfterAccess(1, TimeUnit.SECONDS)
                //可以指定调度程序来及时删除过期缓存项，而不是等待Caffeine触发定期维护
                //若不设置scheduler，则缓存会在下一次调用get的时候才会被动删除
                .scheduler(Scheduler.systemScheduler())
                .build();
        cache.put(1, 2);
        System.out.println(cache.getIfPresent(1));
        Thread.sleep(3000);
        System.out.println(cache.getIfPresent(1));//null
    }*/

    /*  */

    /**
     * 写入后到期
     *//*
    public static void main(String[] args) throws InterruptedException {
        Cache<Integer, Integer> cache = Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.SECONDS)
                //可以指定调度程序来及时删除过期缓存项，而不是等待Caffeine触发定期维护
                //若不设置scheduler，则缓存会在下一次调用get的时候才会被动删除
                .scheduler(Scheduler.systemScheduler())
                .build();
        cache.put(1, 2);
        Thread.sleep(3000);
        System.out.println(cache.getIfPresent(1));//null
    }
*/
  /*  public static void main(String[] args) {
// 基于不同的过期驱逐策略
        LoadingCache<String, Integer> graphs = Caffeine.newBuilder()
                .expireAfter(new Expiry<String, Integer>() {
                    // 缓存创建后指定时间过期
                    public long expireAfterCreate(String str, Integer integer, long currentTime) {
                        // Use wall clock time, rather than nanotime, if from an external resource
                     *//*   long seconds = graph.creationDate().plusHours(5)
                                .minus(System.currentTimeMillis(), MILLIS)
                                .toEpochSecond();*//*
                        System.out.println("缓存创建后指定时间过期"+currentTime);
                        return TimeUnit.SECONDS.toNanos(integer);
                    }
                    // 缓存更新后指定时间过期
                    public long expireAfterUpdate(String str, Integer integer,
                                                  long currentTime, long currentDuration) {
                        System.out.println(currentTime + "缓存更新后指定时间过期" + currentDuration);
                        return currentDuration;
                    }
                    // 缓存读取后指定时间过期
                    public long expireAfterRead(String str, Integer integer,
                                                long currentTime, long currentDuration) {
                        System.out.println(currentTime + "缓存读取后指定时间过期" + currentDuration);
                        return currentDuration;
                    }
                })
                .build(CaffeineCacheTest::getValueFromDB);
        graphs.put("key", 2000);
        Integer key = graphs.getIfPresent("key");
        System.out.println(key);
    }

    private static Integer getValueFromDB(String key) {
        return 10000;
    }*/
    /*public static void main(String[] args) {

      // 当key和缓存元素都不再存在其他强引用的时候驱逐
        LoadingCache<Cat, AppLog> graphs = Caffeine.newBuilder()
                .weakKeys()
                .weakValues()
                .build(CaffeineCacheTest::getValueFromDB);

       // 当进行GC的时候进行驱逐
        LoadingCache<Cat, AppLog> loadingCache = Caffeine.newBuilder()
                .softValues()
                .build(CaffeineCacheTest::getValueFromDB);
    }

    private static AppLog getValueFromDB(Cat key) {
        return new AppLog();
    }*/
  /*  private static int NUM = 0;
    public static void main(String[] args) throws InterruptedException {
        LoadingCache<Integer, Integer> cache = Caffeine.newBuilder()
                .refreshAfterWrite(1, TimeUnit.SECONDS)
                //模拟获取数据，每次获取就自增1
                .build(integer -> ++NUM);

        //获取ID=1的值，由于缓存里还没有，所以会自动放入缓存
        System.out.println(cache.get(1));// 1
        // 延迟2秒后，理论上自动刷新缓存后取到的值是2
        // 但其实不是，值还是1，因为refreshAfterWrite并不是设置了n秒后重新获取就会自动刷新
        // 而是x秒后&&第二次调用getIfPresent的时候才会被动刷新
        Thread.sleep(2000);
        System.out.println(cache.getIfPresent(1));// 1

        //此时才会刷新缓存，而第一次拿到的还是旧值
        System.out.println(cache.getIfPresent(1));// 2
    }*/
    public static void main(String[] args) {
        LoadingCache<String, String> cache = Caffeine.newBuilder()
                //创建缓存或者最近一次更新缓存后经过指定时间间隔，刷新缓存；refreshAfterWrite仅支持LoadingCache
                .refreshAfterWrite(1, TimeUnit.SECONDS)
                .expireAfterWrite(1, TimeUnit.SECONDS)
                .expireAfterAccess(1, TimeUnit.SECONDS)
                .maximumSize(10)
                //开启记录缓存命中率等信息
                .recordStats()
                //根据key查询数据库里面的值
                .build(key -> {
                    Thread.sleep(1000);
                    return new Date().toString();
                });

        cache.put("1", "shawn");
        System.out.println(cache.getIfPresent("1")); ;
        CacheStats stats = cache.stats();
        System.out.println(stats);
        System.out.println(stats.hitCount());
    }
}
