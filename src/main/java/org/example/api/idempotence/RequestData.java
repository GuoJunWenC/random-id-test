package org.example.api.idempotence;

import lombok.Data;
/**
 * 请求数据
 *
 */
@Data
public class RequestData<T> {

    /**
     * 请求数据的值
     */
    private String value;

    /**
     * 请求数据的主体
     */
    private T body;

}
