/*
 * 作者：钟勋 (email:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2016-12-16 01:14 创建
 */
package org.antframework.event.listener;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 监听器中心
 */
public class ListenerHub {
    // 数据类型-监听器集合map
    private final Map<Class<? extends DataType>, List<Listener>> dataTypeListenerses = new ConcurrentHashMap<>();

    /**
     * 获取所有数据类型
     *
     * @return 所有数据类型
     */
    public Set<Class<? extends DataType>> getDataTypes() {
        return Collections.unmodifiableSet(dataTypeListenerses.keySet());
    }

    /**
     * 新增监听器
     *
     * @param listener 监听器
     */
    public void addListener(Listener listener) {
        dataTypeListenerses.compute(listener.getDataType(), (dataType, listeners) -> {
            if (listeners != null && listeners.contains(listener)) {
                return listeners;
            }
            if (listeners == null) {
                listeners = new ArrayList<>(1);
            } else {
                List<Listener> newListeners = new ArrayList<>(listeners.size() + 1);
                newListeners.addAll(listeners);
                listeners = newListeners;
            }
            listeners.add(listener);
            listeners.sort(Comparator.comparingInt(Listener::getPriority));
            return listeners;
        });
    }

    /**
     * 删除监听器
     *
     * @param listener 监听器
     */
    public void removeListener(Listener listener) {
        dataTypeListenerses.computeIfPresent(listener.getDataType(), (dataType, listeners) -> {
            if (!listeners.contains(listener)) {
                return listeners;
            }
            listeners = new ArrayList<>(listeners);
            listeners.remove(listener);
            if (listeners.isEmpty()) {
                listeners = null;
            }
            return listeners;
        });
    }

    /**
     * 获取指定数据类型的所有监听器
     *
     * @param dataType 数据类型
     * @return 指定数据类型的所有监听器
     */
    public List<Listener> getListeners(Class<? extends DataType> dataType) {
        List<Listener> listeners = dataTypeListenerses.get(dataType);
        if (listeners == null) {
            listeners = Collections.emptyList();
        } else {
            listeners = Collections.unmodifiableList(listeners);
        }
        return listeners;
    }
}
