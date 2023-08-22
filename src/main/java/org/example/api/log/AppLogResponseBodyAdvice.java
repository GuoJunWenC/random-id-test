package org.example.api.log;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class AppLogResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    /*    @Value("${app-log.response.body.print:false}")*/
    private boolean printBody = Boolean.TRUE;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 由于返回体一般情况下都比较大，因此通过配置来确定是否需要打印返回体
        return printBody;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // 将返回体设置到log对象中
        AppLogContextHolder.get().setRespBody(body);
        return body;
    }

}