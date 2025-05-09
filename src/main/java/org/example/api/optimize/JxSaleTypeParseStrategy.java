package org.example.api.optimize;

import org.springframework.stereotype.Component;

/**
 * 策略实现
 */
@Component
public class JxSaleTypeParseStrategy implements SaleTypeParseStrategy {
    @Override
    public Integer parse(SaleTypeParseContext saleTypeParseContext) {
        return SaleTypeIntEnum.JX.getCode();
    }

    /*
     * @Override
     * public SaleTypeStrEnum support() {
     * return SaleTypeStrEnum.JX;
     * }
     */
    @Override
    public boolean support(SaleTypeParseContext saleTypeParseContext) {

        return SaleTypeStrEnum.JX.equals(saleTypeParseContext.getSaleTypeStr());
    }
}
