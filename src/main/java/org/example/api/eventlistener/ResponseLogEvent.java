package org.example.api.eventlistener;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ResponseLogEvent implements Serializable {
    private static final long serialVersionUID = 4344198495210544618L;
    /**
     * 时间
     */
    private LocalDateTime time;
    /**
     * 参数
     */
    private String params;
    /**
     * 说明
     */
    private String message;
    /**
     * ip地址
     */
    private String ipAddress;
}
