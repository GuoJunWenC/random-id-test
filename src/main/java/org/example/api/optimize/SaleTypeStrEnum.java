package org.example.api.optimize;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
@Getter
@AllArgsConstructor
public enum SaleTypeStrEnum {
    JX,
    // OTHERS
    DX
    ;
    /**
     * 预热转换关系到内存
     */
    private static Map<String, SaleTypeStrEnum> NAME_MAP = Arrays.stream(SaleTypeStrEnum.values()).collect(Collectors.toMap(SaleTypeStrEnum::name, Function.identity()));

    public static SaleTypeStrEnum getByName(String saleTypeStr){
        return NAME_MAP.get(saleTypeStr);
    }
}
