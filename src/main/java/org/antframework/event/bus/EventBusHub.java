/* 
 * 作者：钟勋 (email:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2016-12-16 01:14 创建
 */
package org.antframework.event.bus;

import org.antframework.event.listener.ListenerType;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 事件总线中心
 */
public class EventBusHub {
    // 监听器类型-事件总线map
    private final Map<Class<? extends ListenerType>, EventBus> listenerTypeEventBuses = new ConcurrentHashMap<>();

    /**
     * 获取所有类型
     *
     * @return 所有类型
     */
    public Set<Class<? extends ListenerType>> getTypes() {
        return Collections.unmodifiableSet(new HashSet<>(listenerTypeEventBuses.keySet()));
    }

    /**
     * 获取事件总线（如果不存在该类型的事件总线，则新创建一个）
     *
     * @param type 总线类型
     * @return 事件总线
     */
    public EventBus getEventBus(Class<? extends ListenerType> type) {
        return listenerTypeEventBuses.computeIfAbsent(type, DefaultEventBus::new);
    }
}
