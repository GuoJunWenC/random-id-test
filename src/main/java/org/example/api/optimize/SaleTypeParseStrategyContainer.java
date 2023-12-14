package org.example.api.optimize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class SaleTypeParseStrategyContainer {
    /* public final static Map<SaleTypeStrEnum, SaleTypeParseStrategy> STRATEGY_MAP = new HashMap<>();

     @PostConstruct
     public void init() {
         STRATEGY_MAP.put(SaleTypeStrEnum.JX, new JxSaleTypeParseStrategy());
         // 继续拓展
         STRATEGY_MAP.put(SaleTypeStrEnum.DX, new DxSaleTypeParseStrategy());
     }

     public Integer parse(SaleTypeParseContext saleTypeParseContext) {
         return Optional.ofNullable(STRATEGY_MAP.get(saleTypeParseContext.getSaleTypeStr())).map(strategy -> strategy.parse(saleTypeParseContext)).orElse(null);
     }*/
 /*  public final static Map<SaleTypeStrEnum, SaleTypeParseStrategy> STRATEGY_MAP = new HashMap<>();
    @Autowired
    private List<SaleTypeParseStrategy> parseStrategyList;

    @PostConstruct
    public void init(){
        parseStrategyList.forEach(strategy-> STRATEGY_MAP.put(strategy.support(), strategy));
    }
    public Integer parse(SaleTypeParseContext saleTypeParseContext){
        return Optional.ofNullable(STRATEGY_MAP.get(saleTypeParseContext.getSaleTypeStr())).map(strategy-> strategy.parse(saleTypeParseContext)).orElse(null);
    }*/
    @Autowired
    private List<SaleTypeParseStrategy> parseStrategyList;

    public Integer parse(SaleTypeParseContext saleTypeParseContext) {
        return parseStrategyList.stream()
                .filter(strategy -> strategy.support(saleTypeParseContext))
                .findAny()
                .map(strategy -> strategy.parse(saleTypeParseContext))
                .orElse(null);
    }
}
