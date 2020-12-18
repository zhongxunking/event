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
 * 数据类型工具
 */
public final class DataTypes {
    // 数据类型-事件类型解决器map
    private static final Map<Class<? extends DataType>, DataType.EventTypeResolver> dataTypeEventTypeResolvers = new ConcurrentHashMap<>();

    /**
     * 获取事件类型解决器
     *
     * @param type 数据类型
     * @return 事件类型解决器
     */
    public static DataType.EventTypeResolver getEventTypeResolver(Class<? extends DataType> type) {
        DataType.EventTypeResolver resolver = dataTypeEventTypeResolvers.get(type);
        if (resolver == null) {
            resolver = dataTypeEventTypeResolvers.computeIfAbsent(type, k -> {
                DataType dataType = BeanUtils.instantiate(k);
                return dataType.getResolver();
            });
        }
        return resolver;
    }
}
