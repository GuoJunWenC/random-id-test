package org.example.api.init;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MyEventListener {
    @EventListener
    public void handleContextRefreshedEvent(ContextRefreshedEvent event) {
        // 在这里处理 ContextRefreshedEvent 事件
        System.out.println("ApplicationContext 已经初始化或刷新完成");
    }
}
