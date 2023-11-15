package org.example.api.els.sync.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.api.els.sync.entity.ProductInfo;

import java.util.List;

public interface ProductInfoService extends IService<ProductInfo> {
    /**
     * 查询数据
     *
     * @param searchPram 参数
     * @return List<ProductInfo>
     */
    List<ProductInfo> queryProductInfo(String searchPram);


}
