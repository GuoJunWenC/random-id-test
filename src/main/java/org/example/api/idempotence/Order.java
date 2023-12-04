package org.example.api.idempotence;

import lombok.Data;

import java.io.Serializable;
/**
 * 订单
 */
@Data
public class Order implements Serializable {
    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 序列化版本UID
     */
    private static final long serialVersionUID = 3927020576675110843L;
}
