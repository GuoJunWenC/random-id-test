package org.example.api.els.sync.mapper;

import org.example.api.els.sync.entity.ProductInfoVO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductInfoElasticsearchRepository extends ElasticsearchRepository<ProductInfoVO, Long> {
}
