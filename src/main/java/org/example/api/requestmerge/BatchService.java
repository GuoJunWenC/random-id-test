package org.example.api.requestmerge;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.example.api.els.sync.entity.ProductInfo;
import org.example.api.els.sync.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Future;

@Slf4j
@Service
public class BatchService {
    @Autowired
    private ProductInfoService productInfoService;

    @HystrixCollapser(batchMethod = "getProductInfoBatch", scope = com.netflix.hystrix.HystrixCollapser.Scope.GLOBAL,
            // 请求时间间隔在 20ms 之内的请求会被合并为一个请求,默认为 10ms
            collapserProperties = {
                    @HystrixProperty(name = "timerDelayInMilliseconds", value = "20"),
                    // 设置触发批处理执行之前，在批处理中允许的最大请求数。
                    @HystrixProperty(name = "maxRequestsInBatch", value = "200")
            })
    public Future<ProductInfo> getProductInfoById(Long id) {
        return null;
    }

    @HystrixCommand
    public List<ProductInfo> getProductInfoBatch(List<Long> ids) {
        log.info("批处理,[{}]", ids);
        log.info("批处理,[{}]", ids);
        return productInfoService.listByIds(ids);
    }

}
