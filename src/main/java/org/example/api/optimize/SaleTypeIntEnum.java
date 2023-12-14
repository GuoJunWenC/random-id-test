package org.example.api.optimize;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SaleTypeIntEnum {
    JX(1),
    DX(2)
    // OTHERS
    ;
    private final Integer code;
}