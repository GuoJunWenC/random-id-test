package org.example.api.log;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AppLogInterceptor implements HandlerInterceptor {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获得一个线程上下文log对象
        AppLog appLog = AppLogContextHolder.get();

        // 把请求路径也放入到线程上下文中
        appLog.setPath(request.getServletPath());

        // 把请求参数放入到线程上下文对象中
        appLog.setParameters(request.getParameterMap());

        // 这里也可以把header信息也放进去，我这里就不放了哈
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        AppLog appLog = AppLogContextHolder.get();
        // 打印本次请求的相关日志
        log.info(JSON.toJSONString(appLog));
        // 请求结束后清空掉当前线程上下文的内容，防止内存泄漏
        AppLogContextHolder.remove();
    }}
