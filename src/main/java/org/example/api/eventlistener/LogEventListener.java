package org.example.api.eventlistener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LogEventListener {
    // 监听请求日志事件
    @Order(1)
    @EventListener()
    public void handleNotifyEvent1(LogEventEntity event) {
        log.info("监听到请求日志事件1：" +
                "[{}]", event);

    }

    // 监听请求日志事件
    @Order(2)
    @EventListener()
    public void handleNotifyEvent2(LogEventEntity event) {
        log.info("监听到请求日志事件2：" +
                "[{}]", event);

    }

    // 监听请求日志事件
    @Order(3)
    @EventListener()
    public void handleNotifyEvent3(LogEventEntity event) {
        log.info("监听到请求日志事件3：" +
                "[{}]", event);

    }
}
