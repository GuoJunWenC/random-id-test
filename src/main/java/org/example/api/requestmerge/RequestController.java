package org.example.api.requestmerge;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.SneakyThrows;
import org.example.api.els.sync.entity.ProductInfo;
import org.example.api.els.sync.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@RequestMapping("/request")
@RestController
public class RequestController {
    //存放请求的队列
    LinkedBlockingDeque<RequestTest> queue = new LinkedBlockingDeque<>();
    @Autowired
    private BatchService batchService;
    @Autowired
    private ProductInfoService productInfoService;

    //初始化方法
    @PostConstruct
    public void init() {
        //定时执行的线程池，每隔5毫秒执行一次(间隔时间可以由业务决定)，把所有堆积的请求
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(() -> {
            //在这里具体执行批量查询逻辑
            int size = queue.size();
            if (size == 0) {
                //若没有请求堆积，直接返回，等10毫秒再执行一次
                return;
            }
            //若有请求堆积把所有请求都拿出来
            List<RequestTest> requestTests = Lists.newArrayListWithCapacity(size);
            for (int i = 0; i < size; i++) {
                //把请求拿出来
                RequestTest poll = queue.poll();
                requestTests.add(poll);
            }
            //至此请求已经被合并了
            System.out.print("##############################################\n");
            System.out.printf("请求合并了" + requestTests.size() + "条！\n");
            //组装批量查询条件
            List<Long> idList = Lists.newArrayListWithCapacity(requestTests.size());
            for (RequestTest requestTest : requestTests) {
                idList.add(requestTest.getKey());
            }
            //进行批量查询
            List<ProductInfo> productInfoList = productInfoService.listByIds(idList);
            if (CollectionUtils.isEmpty(productInfoList)) {
                return;
            }
            //把批查结果放入一个map
            Map<Long, String> map = Maps.newHashMapWithExpectedSize(productInfoList.size());
            for (ProductInfo productInfo : productInfoList) {
                map.put(productInfo.getId(), productInfo.getProductName());
            }
            for (RequestTest requestTest : requestTests) {
                //把放在map中的结果集放回给对应的线程
                //future是对应每个请求的，因为是每个请求线程都传了自己的future是对应的过来
                requestTest.getFuture().complete(map.get(requestTest.getKey()));

            }
        }, 0, 5, TimeUnit.MILLISECONDS);
    }

    //请求合并
    @SneakyThrows
    @GetMapping("/requestMerge/getProductInfoById")
    public String getProductInfoMerge(Long id) throws ExecutionException, TimeoutException {
        long currentMillis = System.currentTimeMillis();
        //CompletableFuture可以使一个线程执行操作后，主动返回值给另一个线程
        CompletableFuture<String> future = new CompletableFuture<>();
        RequestTest requestTest = new RequestTest();
        //把future(把future可以认为是线程间的"传话人")放到等待队列中去，让定时调度的线程池执行并返回值
        requestTest.setFuture(future);
        requestTest.setKey(id);
        //把requestTest加入等待队列(LinkedBlockingDeque)
        queue.add(requestTest);
        //future(传话人)阻塞直到有值返回
        String productName = future.get(2, TimeUnit.SECONDS);
        System.out.printf("商品名为:" + productName + "---线程名为:" + Thread.currentThread().getName() +
                "---执行时间为:" + (System.currentTimeMillis() - currentMillis) + "\n");
        return productName;
    }

    /**
     * 请求合并接口
     *
     * @param id 主键id
     * @return ProductInfo
     */
    @SneakyThrows
    @GetMapping("/requestMergeHystrix/getProductInfoById")
    public ProductInfo getProductInfoById(Long id) {
        Future<ProductInfo> byId = batchService.getProductInfoById(id);
        return byId.get();
    }
}
