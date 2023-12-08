package org.example.api.els.sync.controller;

import org.example.api.els.sync.entity.ProductInfo;
import org.example.api.els.sync.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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