/* 
 * 作者：钟勋 (email:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2016-12-16 01:14 创建
 */
package org.antframework.event.listener;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 监听器中心
 */
public class ListenerHub {
    // 数据类型-监听器集合map
    private final Map<Class<? extends DataType>, Set<Listener>> dataTypeListenerses = new ConcurrentHashMap<>();

    /**
     * 新增监听器
     *
     * @param listener 监听器
     */
    public void addListener(Listener listener) {
        dataTypeListenerses.compute(listener.getDataType(), (dataType, listeners) -> {
            if (listeners == null) {
                listeners = new HashSet<>();
            }
            listeners.add(listener);
            return listeners;
        });
    }

    /**
     * 获取所有数据类型
     *
     * @return 所有数据类型
     */
    public Set<Class<? extends DataType>> getDataTypes() {
        return Collections.unmodifiableSet(new HashSet<>(dataTypeListenerses.keySet()));
    }

    /**
     * 获取指定数据类型的所有监听器
     *
     * @param dataType 数据类型
     * @return 指定数据类型的所有监听器
     */
    public Set<Listener> getListeners(Class<? extends DataType> dataType) {
        Set<Listener> listeners = new HashSet<>();
        dataTypeListenerses.computeIfPresent(dataType, (k, v) -> {
            listeners.addAll(v);
            return v;
        });
        return Collections.unmodifiableSet(listeners);
    }
}
