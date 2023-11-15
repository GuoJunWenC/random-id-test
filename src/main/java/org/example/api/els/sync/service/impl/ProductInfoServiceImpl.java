package org.example.api.els.sync.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.example.api.els.sync.entity.ProductInfo;
import org.example.api.els.sync.mapper.ProductInfoMapper;
import org.example.api.els.sync.mapper.ProductInfoElasticsearchRepository;
import org.example.api.els.sync.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

@Slf4j
@Service
public class ProductInfoServiceImpl extends ServiceImpl<ProductInfoMapper, ProductInfo> implements ProductInfoService {
    @Autowired
    private ProductInfoElasticsearchRepository productInfoElasticsearchRepository;
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /*  @Override
      public List<ProductInfo> queryProductInfo(String searchPram) {
          QueryBuilder queryBuilder  = QueryBuilders.fuzzyQuery("productName", searchPram).fuzziness(Fuzziness.AUTO);
          Iterable<ProductInfoVO> search = productInfoElasticsearchRepository.search(queryBuilder);
          List<ProductInfoVO> users = new ArrayList<>();
          for (ProductInfoVO productInfoVO : search) {
              users.add(productInfoVO);
          }
          Optional<ProductInfoVO> byId = productInfoElasticsearchRepository.findById(1L);

          return baseMapper.queryProductInfo();
      }*/
    @SneakyThrows
    @Override
    public List<ProductInfo> queryProductInfo(String searchPram) {
        // TODO ES整合
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(searchPram,
                "productName",
                "productCode").analyzer("ik_smart").fuzziness(Fuzziness.ONE);
        FuzzyQueryBuilder fuzzyQuery = QueryBuilders.fuzzyQuery("productName", searchPram).fuzziness(Fuzziness.TWO);

        searchSourceBuilder.query(multiMatchQueryBuilder);
        ;
        SearchRequest searchRequest = new SearchRequest(new String[]{"product_info"},
                searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SearchHits hits = searchResponse.getHits();
        SearchHit[] hitsList = hits.getHits();
        List<Integer> ids = new ArrayList<>();
        for (SearchHit documentFields : hitsList) {
            ids.add(Integer.parseInt(documentFields.getId()));
        }
        System.out.println(ids);
        return null;
    }


}
