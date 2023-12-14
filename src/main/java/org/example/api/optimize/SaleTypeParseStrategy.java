package org.example.api.optimize;


/**
 * 定义策略接口
 */
public interface SaleTypeParseStrategy {
    Integer parse(SaleTypeParseContext saleTypeParseContext);

    /*  // 所支持的情况
      SaleTypeStrEnum support();*/
    boolean support(SaleTypeParseContext saleTypeParseContext);

}