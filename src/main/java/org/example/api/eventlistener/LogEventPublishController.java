package org.example.api.eventlistener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/logEventPublish")
public class LogEventPublishController {
    @Resource
    private ApplicationContext applicationContext;

    /**
     * 发布事件
     */
    @GetMapping("/publish")
    public void publish() {
        LogEventEntity logEventEntity = new LogEventEntity();
        logEventEntity.setTime(LocalDateTime.now());
        logEventEntity.setParams("A=A");
        logEventEntity.setMessage("message");
        logEventEntity.setIpAddress("127.0.0.1");
        applicationContext.publishEvent(logEventEntity);
    }

    /**
     * 发布事件
     */
    @GetMapping("/publishResponseEvent")
    public void publishResponseEvent() {
        ResponseLogEvent logEventEntity = new ResponseLogEvent();
        logEventEntity.setTime(LocalDateTime.now());
        logEventEntity.setParams("A=A");
        logEventEntity.setMessage("message");
        logEventEntity.setIpAddress("127.0.0.1");
        applicationContext.publishEvent(logEventEntity);
    }
}
