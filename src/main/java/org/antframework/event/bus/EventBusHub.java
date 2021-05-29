/*
 * 作者：钟勋 (email:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2016-12-16 01:14 创建
 */
package org.antframework.event.bus;

import lombok.RequiredArgsConstructor;
import org.antframework.event.bus.core.DefaultEventBus;
import org.antframework.event.listener.DataType;
import org.antframework.filter.FilterHub;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 事件总线中心
 */
@RequiredArgsConstructor
public class EventBusHub {
    // 数据类型-事件总线map
    private final Map<Class<? extends DataType>, EventBus> dataTypeEventBuses = new ConcurrentHashMap<>();
    // 过滤器中心
    private final FilterHub filterHub;

    /**
     * 获取所有数据类型
     *
     * @return 所有数据类型
     */
    public Set<Class<? extends DataType>> getDataTypes() {
        return Collections.unmodifiableSet(dataTypeEventBuses.keySet());
    }

    /**
     * 获取事件总线（如果不存在该数据类型的事件总线，则新创建一个）
     *
     * @param dataType 数据类型
     * @return 事件总线
     */
    public EventBus getEventBus(Class<? extends DataType> dataType) {
        return dataTypeEventBuses.computeIfAbsent(dataType, k -> new DefaultEventBus(k, filterHub));
    }
}
