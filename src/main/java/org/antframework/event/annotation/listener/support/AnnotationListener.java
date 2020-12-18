/* 
 * 作者：钟勋 (email:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2016-12-16 01:14 创建
 */
package org.antframework.event.annotation.listener.support;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.antframework.event.annotation.listener.ListenResolver;
import org.antframework.event.listener.DataType;
import org.antframework.event.listener.DataTypes;
import org.antframework.event.listener.Listener;
import org.antframework.event.listener.PriorityType;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 注解监听器
 */
@AllArgsConstructor
public class AnnotationListener implements Listener {
    // 数据类型
    private final Class<? extends DataType> dataType;
    // 优先级
    private final int priority;
    // 监听器
    private final Object listener;
    // 事件类型-监听执行器map
    private final Map<Object, ListenExecutor> eventTypeListenExecutors;

    @Override
    public Class<? extends DataType> getDataType() {
        return dataType;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public Set<Object> getEventTypes(PriorityType priorityType) {
        Set<Object> eventTypes = new HashSet<>();
        eventTypeListenExecutors.forEach((eventType, listenExecutor) -> {
            if (listenExecutor.getPriorityType() == priorityType) {
                eventTypes.add(eventType);
            }
        });
        return Collections.unmodifiableSet(eventTypes);
    }

    @Override
    public void execute(Object event) throws Throwable {
        Object eventType = DataTypes.getEventTypeResolver(dataType).resolve(event);
        ListenExecutor listenExecutor = eventTypeListenExecutors.get(eventType);
        if (listenExecutor != null) {
            listenExecutor.execute(listener, event);
        }
    }

    /**
     * 监听执行器
     */
    @AllArgsConstructor
    public static class ListenExecutor {
        // 监听解决器
        private final ListenResolver resolver;
        // 是否优先级升序
        @Getter
        private final PriorityType priorityType;
        // 监听方法
        private final Method listenMethod;

        /**
         * 执行监听
         *
         * @param listener 监听器
         * @param event    事件
         * @throws Throwable 执行过程中发生任何异常都会往外抛
         */
        public void execute(Object listener, Object event) throws Throwable {
            try {
                listenMethod.invoke(listener, resolver.resolveParams(event));
            } catch (InvocationTargetException e) {
                // 抛出原始异常
                throw e.getTargetException();
            }
        }

        /**
         * 获取事件类型
         */
        public Object getEventType() {
            return resolver.getEventType();
        }
    }
}
