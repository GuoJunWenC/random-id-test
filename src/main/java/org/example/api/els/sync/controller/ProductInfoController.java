package org.example.api.els.sync.controller;

import org.example.api.els.sync.entity.ProductInfo;
import org.example.api.els.sync.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/productInfo")
public class ProductInfoController {
    @Autowired
    private ProductInfoService productInfoService;

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
}
