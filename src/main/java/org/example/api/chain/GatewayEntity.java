package org.example.api.chain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GatewayEntity {
    private Integer handlerId;
    private String name;

    private String conference;

    private Integer preHandlerId;

    private Integer nextHandlerId;
}
