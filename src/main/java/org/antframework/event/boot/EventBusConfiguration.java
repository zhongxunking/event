/* 
 * 作者：钟勋 (email:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2016-12-16 01:14 创建
 */
package org.antframework.event.boot;

import lombok.AllArgsConstructor;
import org.antframework.event.EventPublisher;
import org.antframework.event.annotation.listener.Listener;
import org.antframework.event.bus.EventBus;
import org.antframework.event.bus.EventBusHub;
import org.antframework.event.extension.ListenerType;
import org.antframework.event.extension.support.DomainListenerType;
import org.antframework.event.listener.ListenerExecutor;
import org.antframework.event.listener.ListenerHub;
import org.antframework.event.listener.ListenerParser;
import org.antframework.event.publisher.DefaultEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;

/**
 * 事件总线配置
 */
@Configuration
@Import({EventBusHub.class,
        ListenerHub.class,
        EventBusConfiguration.ListenerScanner.class,
        EventBusConfiguration.EventBusInitializer.class})
public class EventBusConfiguration {
    /**
     * 优先级
     */
    public static final int ORDER = 0;

    // 配置领域事件发布器
    @Bean
    public EventPublisher eventPublisher(EventBusHub eventBusHub) {
        return new DefaultEventPublisher(eventBusHub.getEventBus(DomainListenerType.class));
    }

    /**
     * 监听器扫描器
     */
    @Order(ORDER)
    @AllArgsConstructor
    public static class ListenerScanner implements ApplicationListener<ContextRefreshedEvent> {
        // 监听器中心
        private final ListenerHub listenerHub;

        @Override
        public void onApplicationEvent(ContextRefreshedEvent event) {
            // 扫描
            String[] beanNames = event.getApplicationContext().getBeanNamesForAnnotation(Listener.class);
            for (String beanName : beanNames) {
                Object bean = event.getApplicationContext().getBean(beanName);
                // 解析
                ListenerExecutor listenerExecutor = ListenerParser.parseListener(bean);
                // 注册
                listenerHub.addListener(listenerExecutor);
            }
        }
    }

    /**
     * 事件总线初始化器
     */
    @Order(ORDER + 100)
    @AllArgsConstructor
    public static class EventBusInitializer implements ApplicationListener<ContextRefreshedEvent> {
        // 数据总线中心
        private final EventBusHub eventBusHub;
        // 监听器中心
        private final ListenerHub listenerHub;

        @Override
        public void onApplicationEvent(ContextRefreshedEvent event) {
            for (Class<? extends ListenerType> type : listenerHub.getTypes()) {
                // 初始化事件总线
                EventBus eventBus = eventBusHub.getEventBus(type);
                for (ListenerExecutor listenerExecutor : listenerHub.getListeners(type)) {
                    eventBus.addListenerExecutor(listenerExecutor);
                }
            }
        }
    }
}
