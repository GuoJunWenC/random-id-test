package org.example.api.optimize;

import org.springframework.stereotype.Component;

@Component
public class SaleTypeService {
    public static Integer parseSaleType(String saleTypeStr) {
        SaleTypeStrEnum saleTypeEnum = SaleTypeStrEnum.getByName(saleTypeStr);
        SaleTypeParseContext context = new SaleTypeParseContext();
        context.setSaleTypeStr(saleTypeEnum);
        SaleTypeParseStrategyContainer saleTypeParseStrategyContainer = new SaleTypeParseStrategyContainer();
        return saleTypeParseStrategyContainer.parse(context);
    }
}
