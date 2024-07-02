package org.example.api.els.sync.controller;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.example.api.els.sync.entity.ProductInfo;
import org.example.api.els.sync.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.*;

@CacheConfig(cacheNames = "caffeineCacheManager")
@RestController
@RequestMapping("/productInfo")
public class ProductInfoController {
    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private RestTemplate restTemplate;
    /**
     * 查询数据
     *
     * @param searchPram 参数
     * @return List<ProductInfo>
     */
    @GetMapping("/queryProductInfo")
    public List<ProductInfo> queryProductInfo(String searchPram) {
        return productInfoService.queryProductInfo(searchPram);
    }
    public static void main(String[] args) {
        Cache<String, WeakReference<String>> cache = CacheBuilder.newBuilder()
                .maximumSize(100) // 最大缓存大小
                .expireAfterWrite(10, TimeUnit.MICROSECONDS) // 缓存过期时间
                .build();

        String key = "testKey";
        String value = "testValue";
        cache.put(key, new WeakReference<>(value));

        WeakReference<String> weakValue = cache.getIfPresent(key);
        if (weakValue != null) {
            String actualValue = weakValue.get();
            if (actualValue != null) {
                System.out.println("缓存中存在键为 " + key + " 的值：" + actualValue);
            } else {
                System.out.println("缓存中键为 " + key + " 的值已被回收");
            }
        } else {
            System.out.println("缓存中不存在键为 " + key + " 的值");
        }

        cache.invalidate(key); // 从缓存中移除键为 key 的项
    }
    /**
     * 通过id查询商品
     *
     * @param id id
     * @return R
     */
    @Cacheable(key = "#id")
    @GetMapping("/{id}")
    public ProductInfo getById(@PathVariable("id") Integer id) {
        return productInfoService.getById(id);
    }

    /**
     * 新增商品
     *
     * @param productInfo 商品
     * @return Boolean
     */
    @CachePut(key = "#productInfo.id")
    @PostMapping
    public Boolean save(@RequestBody ProductInfo productInfo) {
        productInfoService.save(productInfo);
        return Boolean.TRUE;
    }

    @GetMapping("/requestProductInfo")
    public void requestProductInfo() {
        ExecutorService executorService = Executors.newFixedThreadPool(20);//模拟20个并发请求
        CountDownLatch countDownLatch = new CountDownLatch(20);
        for (int i = 1; i <= 20; i++) {
            int finalI = i;
            executorService.execute(() -> {
                try {
                    countDownLatch.await();
                    ProductInfo forObject = restTemplate.getForObject("http://127.0.0.1:3456/request/requestMergeHystrix/getProductInfoById?id=" + finalI, ProductInfo.class);
                    System.out.println("finalI" + finalI + forObject);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            countDownLatch.countDown();
        }
    }

    /**
     * 修改商品
     *
     * @param productInfo 商品
     * @return Boolean
     */
    @CachePut(key = "#productInfo.id")
    @PutMapping
    public Boolean updateById(@RequestBody ProductInfo productInfo) {
        productInfoService.updateById(productInfo);
        return Boolean.TRUE;
    }

    /**
     * 通过id删除商品
     *
     * @param id id
     * @return Boolean
     */
    @CacheEvict(key = "#id")
    @DeleteMapping("/{id}")
    public Boolean removeById(@PathVariable Integer id) {
        productInfoService.removeById(id);
        return Boolean.TRUE;
    }
}