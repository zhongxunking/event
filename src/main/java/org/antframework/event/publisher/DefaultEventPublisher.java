/*
 * 作者：钟勋 (email:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2016-12-16 01:14 创建
 */
package org.antframework.event.publisher;

import lombok.AllArgsConstructor;
import org.antframework.event.EventPublisher;
import org.antframework.event.bus.EventBus;
import org.antframework.event.common.Exceptions;

/**
 * 事件发布器的默认实现
 */
@AllArgsConstructor
public class DefaultEventPublisher implements EventPublisher {
    // 事件总线
    private final EventBus eventBus;

    @Override
    public void publish(Object event) {
        try {
            eventBus.dispatch(event);
        } catch (Throwable e) {
            Exceptions.rethrow(e);
        }
    }
}
