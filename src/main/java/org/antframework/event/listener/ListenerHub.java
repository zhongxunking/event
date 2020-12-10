/* 
 * 作者：钟勋 (email:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2016-12-16 01:14 创建
 */
package org.antframework.event.listener;

import org.antframework.event.extension.ListenerType;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 监听器中心
 */
public class ListenerHub {
    // 监听器类型-监听器执行器集合map
    private final Map<Class<? extends ListenerType>, Set<ListenerExecutor>> listenerTypeListenerExecutorses = new ConcurrentHashMap<>();

    /**
     * 新增监听器
     *
     * @param listenerExecutor 监听器执行器
     */
    public void addListener(ListenerExecutor listenerExecutor) {
        listenerTypeListenerExecutorses.compute(listenerExecutor.getType(), (type, listenerExecutors) -> {
            if (listenerExecutors == null) {
                listenerExecutors = new HashSet<>();
            }
            listenerExecutors.add(listenerExecutor);
            return listenerExecutors;
        });
    }

    /**
     * 获取所有监听器类型
     *
     * @return 所有监听器类型
     */
    public Set<Class<? extends ListenerType>> getTypes() {
        return Collections.unmodifiableSet(new HashSet<>(listenerTypeListenerExecutorses.keySet()));
    }

    /**
     * 获取指定类型的所有监听器
     *
     * @param type 类型
     * @return 指定类型的所有监听器
     */
    public Set<ListenerExecutor> getListeners(Class<? extends ListenerType> type) {
        Set<ListenerExecutor> listenerExecutors = new HashSet<>();
        listenerTypeListenerExecutorses.computeIfPresent(type, (k, v) -> {
            listenerExecutors.addAll(v);
            return v;
        });
        return Collections.unmodifiableSet(listenerExecutors);
    }
}
