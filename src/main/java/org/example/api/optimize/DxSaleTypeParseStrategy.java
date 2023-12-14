package org.example.api.optimize;

import org.springframework.stereotype.Component;

/**
 * 策略实现
 */
@Component
public class DxSaleTypeParseStrategy implements SaleTypeParseStrategy {
    @Override
    public Integer parse(SaleTypeParseContext saleTypeParseContext) {
        return SaleTypeIntEnum.DX.getCode();
    }

    /* @Override
     public SaleTypeStrEnum support() {
         return SaleTypeStrEnum.DX;
     }*/
    @Override
    public boolean support(SaleTypeParseContext saleTypeParseContext) {
        return SaleTypeStrEnum.DX.equals(saleTypeParseContext.getSaleTypeStr());
    }
}
