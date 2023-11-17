package org.example.api.exception;

import lombok.Data;
import org.example.api.enums.ErrorCodeEnum;

import java.io.Serializable;

/**
 * 异常简易返回信息
 */
@Data
public class ErrorResponseMessage implements Serializable {
    private static final long serialVersionUID = -3213302792125481771L;
    private Integer errorCode;
    private String errorMsg;

    public ErrorResponseMessage() {
    }

    public ErrorResponseMessage(Integer errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public ErrorResponseMessage(ErrorCodeEnum errorCode) {
        this.errorCode = errorCode.getCode();
        this.errorMsg = errorCode.getMessage();
    }

    public ErrorResponseMessage(ErrorCodeEnum errorCode, String errorMsg) {
        this.errorCode = errorCode.getCode();
        this.errorMsg = errorMsg;
    }
}
