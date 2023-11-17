package org.example.api.exception;


import lombok.extern.slf4j.Slf4j;
import org.example.api.enums.ErrorCodeEnum;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.management.OperationsException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 管理平台全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class AppExceptionHandler {
    @ExceptionHandler
    public ErrorResponseMessage handleAndReturnData(HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response, Exception ex) {
        ErrorResponseMessage message = new ErrorResponseMessage();
        if (ex instanceof ErrorCodeException) {
            ErrorCodeException e = (ErrorCodeException) ex;
            log.warn("[{}]接口异常ErrorCodeException[{}]", request.getRequestURI(), e);
            message.setErrorCode(e.getCode());
            message.setErrorMsg(e.getMessage());
            return message;
        }
        if (ex instanceof OperationsException) {
            OperationsException e = (OperationsException) ex;
            log.warn("[{}]接口异常OperationsException[{}]", request.getRequestURI(), e);
            message.setErrorCode(ErrorCodeEnum.ERROR.getCode());
            message.setErrorMsg(e.getMessage());
            return message;
        }
        if (ex instanceof NullPointerException) {
            NullPointerException e = (NullPointerException) ex;
            log.error("[{}]接口异常-NullPointerException[{}]", request.getRequestURI(), e);
            message.setErrorCode(ErrorCodeEnum.ERROR.getCode());
            message.setErrorMsg(e.getMessage());
            return message;
        }
        //处理未知异常
        log.error("[{}]系统异常-未知异常[{}]", request.getRequestURI(), ex);
        message.setErrorCode(ErrorCodeEnum.ERROR.getCode());
        message.setErrorMsg("系统异常");
        return message;
    }


    /**
     * 异常处理
     *
     * @param request 请求
     * @param e       异常
     * @return SimpleMessage
     */
    @ExceptionHandler(value = IOException.class)
    public ErrorResponseMessage ioErrorHandler(HttpServletRequest request, IOException e) {
        ErrorResponseMessage message = new ErrorResponseMessage();
        String error = "java.io.IOException: Broken pipe";
        if (e.getMessage().contains(error)) {
            log.warn("[{}]接口异常[{}]", request.getRequestURI(), e.getMessage(), e);
            message.setErrorCode(ErrorCodeEnum.ERROR.getCode());
            message.setErrorMsg(e.getMessage());
            return message;
        }
        log.error("[{}]接口异常[{}]", request.getRequestURI(), e.getMessage(), e);
        message.setErrorCode(ErrorCodeEnum.ERROR.getCode());
        message.setErrorMsg(e.getMessage());
        return message;
    }
}