package org.example.api.optimize;

import lombok.Data;


/**
 * 调用上下文
 */
@Data
public class SaleTypeParseContext {
    private SaleTypeStrEnum saleTypeStr;

    private SaleTypeParseStrategy parseStrategy;

    public Integer parse() {
        return parseStrategy.parse(this);
    }
}