package org.example.api.optimize;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum SaleTypeRelEnum {
    // 不在分别定义两类变量，而是直接定义变量映射关系
    JX("JX", 1),
    // OTHERS
    ;
    private final String fromCode;
    private final Integer toCode;

    private static final Map<String, SaleTypeRelEnum> FROM_CODE_MAP = Arrays.stream(SaleTypeRelEnum.values()).collect(Collectors.toMap(SaleTypeRelEnum::getFromCode, Function.identity()));

    public static SaleTypeRelEnum get(String saleTypeStr) {
        return FROM_CODE_MAP.get(saleTypeStr);
    }

    public static Integer parseCode(String saleTypeStr) {
        return Optional.ofNullable(SaleTypeRelEnum.get(saleTypeStr)).map(SaleTypeRelEnum::getToCode).orElse(null);
    }
}