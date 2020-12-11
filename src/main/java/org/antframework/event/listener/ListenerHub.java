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
    // 类型-监听器集合map
    private final Map<Class<? extends ListenerType>, Set<Listener>> typeListenerses = new ConcurrentHashMap<>();

    /**
     * 新增监听器
     *
     * @param listener 监听器
     */
    public void addListener(Listener listener) {
        typeListenerses.compute(listener.getType(), (type, listeners) -> {
            if (listeners == null) {
                listeners = new HashSet<>();
            }
            listeners.add(listener);
            return listeners;
        });
    }

    /**
     * 获取所有监听器类型
     *
     * @return 所有监听器类型
     */
    public Set<Class<? extends ListenerType>> getTypes() {
        return Collections.unmodifiableSet(new HashSet<>(typeListenerses.keySet()));
    }

    /**
     * 获取指定类型的所有监听器
     *
     * @param type 类型
     * @return 指定类型的所有监听器
     */
    public Set<Listener> getListeners(Class<? extends ListenerType> type) {
        Set<Listener> listeners = new HashSet<>();
        typeListenerses.computeIfPresent(type, (k, v) -> {
            listeners.addAll(v);
            return v;
        });
        return Collections.unmodifiableSet(listeners);
    }
}
