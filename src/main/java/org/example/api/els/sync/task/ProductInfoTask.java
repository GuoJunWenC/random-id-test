package org.example.api.els.sync.task;

import lombok.SneakyThrows;
import org.elasticsearch.client.RestHighLevelClient;
import org.example.api.els.sync.entity.ProductInfo;
import org.example.api.els.sync.entity.ProductInfoVO;
import org.example.api.els.sync.mapper.ProductInfoMapper;
import org.example.api.els.sync.mapper.ProductInfoElasticsearchRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductInfoTask {
    @Autowired
    private ProductInfoMapper productInfoMapper;
    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Autowired
    private ProductInfoElasticsearchRepository productInfoElasticsearchRepository;

    @SneakyThrows
    //@Scheduled(cron = "0/1 * * * * ?")
    public void syncProductInfo() {
        List<ProductInfo> productInfoList = productInfoMapper.queryProductInfo();
        for (ProductInfo productInfo : productInfoList) {
            ProductInfoVO productInfoVO = new ProductInfoVO();
            BeanUtils.copyProperties(productInfo,productInfoVO);
            productInfoElasticsearchRepository.save(productInfoVO);
        }
     /*   List<ProductInfo> productInfoList = productInfoMapper.queryProductInfo();
     *//*   userElasticsearchRepository.saveAll(productInfoList);
        System.out.println();*//*

        BulkRequest bulkRequest = new BulkRequest("product_info");
        productInfoList.forEach(b -> {
            bulkRequest.add(
                new IndexRequest("product_info")
                        .id(String.valueOf(b.getId())) // 指定文档id，不指定则取默认值
                        .source("id",b.getId(),"productCod",b.getProductCode(),"productName",b.getProductName(),"productPrice",b.getProductPrice()));});
        BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(bulkResponse);*/
    }
}
