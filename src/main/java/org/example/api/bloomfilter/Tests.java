package org.example.api.bloomfilter;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class Tests {
    static BloomFilter<Integer> filter = BloomFilter.create(
            //Funnel 是一个接口，用于将任意类型的对象转换为字节流，
            //以便用于布隆过滤器的哈希计算。
            Funnels.integerFunnel(),
            10000000,  // 插入数据条目数量
            0.001);  // 误判率

    @PostConstruct
    public void addProduct() {
        log.info("初始化布隆过滤器数据开始");
        //插入4个元素

        log.info("初始化布隆过滤器数据结束");
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10000000; i++) {
            filter.put(i );
        }

        filter.put(2);
        filter.put(3);
        filter.put(4);
        System.out.println(filter.mightContain(100000000));;
    }
}
