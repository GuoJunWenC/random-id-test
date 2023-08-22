package org.example.api.init;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class MyApplicationListener implements ApplicationListener<ContextRefreshedEvent> {
    /**
     * ContextRefreshedEvent spring 内置事件 extends ApplicationContextEvent
     * ApplicationContext 被初始化或刷新时，该事件被发布
     *
     * @param event the event to respond to
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("ContextRefreshedEventL:"+event);
    }
}
