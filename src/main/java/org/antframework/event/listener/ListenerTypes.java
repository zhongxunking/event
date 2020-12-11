/* 
 * 作者：钟勋 (email:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2020-12-11 19:45 创建
 */
package org.antframework.event.listener;

import org.springframework.beans.BeanUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 监听器类型工具
 */
public final class ListenerTypes {
    // 监听器类型-事件类型解决器map
    private static final Map<Class<? extends ListenerType>, ListenerType.EventTypeResolver> listenerTypeEventTypeResolvers = new ConcurrentHashMap<>();

    /**
     * 获取事件类型解决器
     *
     * @param type 监听器类型
     * @return 事件类型解决器
     */
    public static ListenerType.EventTypeResolver getEventTypeResolver(Class<? extends ListenerType> type) {
        ListenerType.EventTypeResolver resolver = listenerTypeEventTypeResolvers.get(type);
        if (resolver == null) {
            resolver = listenerTypeEventTypeResolvers.computeIfAbsent(type, k -> {
                ListenerType listenerType = BeanUtils.instantiate(k);
                return listenerType.getResolver();
            });
        }
        return resolver;
    }
}
