/* 
 * 作者：钟勋 (email:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2016-12-16 01:14 创建
 */
package org.antframework.event.bus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.antframework.event.listener.Listener;
import org.antframework.event.listener.ListenerType;
import org.antframework.event.listener.ListenerTypes;
import org.antframework.event.listener.PriorityType;
import org.springframework.util.Assert;

import java.util.*;

/**
 * 事件总线的默认实现
 */
@RequiredArgsConstructor
public class DefaultEventBus implements EventBus {
    // 监听器类型
    @Getter
    private final Class<? extends ListenerType> listenerType;
    // 监听器集合
    private final Set<Listener> listeners = new HashSet<>();
    // 分发器
    private Dispatcher dispatcher = new Dispatcher();

    @Override
    public synchronized void addListener(Listener listener) {
        Assert.isTrue(listener.getType() == listenerType, String.format("监听器类型[%s]与事件总线接受类型[%s]不匹配", listener.getType(), listenerType));
        if (!listeners.contains(listener)) {
            listeners.add(listener);
            dispatcher = dispatcher.addListener(listener);
        }

    }

    @Override
    public synchronized void removeListener(Listener listener) {
        if (listener.getType() == listenerType && listeners.contains(listener)) {
            listeners.remove(listener);
            dispatcher = dispatcher.removeListener(listener);
        }
    }

    @Override
    public void dispatch(Object event) throws Throwable {
        dispatcher.dispatch(event);
    }

    // 分发器
    private class Dispatcher {
        // 升序队列
        private final Map<Object, List<Listener>> asc = new HashMap<>();
        // 降序队列
        private final Map<Object, List<Listener>> desc = new HashMap<>();

        // 分发事件
        void dispatch(Object event) throws Throwable {
            Object eventType = ListenerTypes.getEventTypeResolver(listenerType).resolve(event);
            // 向升序队列分发事件
            doDispatch(asc.get(eventType), event);
            // 向降序队列分发事件
            doDispatch(desc.get(eventType), event);
        }

        // 执行分发事件
        private void doDispatch(List<Listener> listeners, Object event) throws Throwable {
            if (listeners != null) {
                for (Listener listener : listeners) {
                    listener.execute(event);
                }
            }
        }

        // 新增监听器（返回新的分发器）
        Dispatcher addListener(Listener listener) {
            Dispatcher nextDispatcher = copy();
            // 调整升序队列
            doAddListener(nextDispatcher.asc, listener, PriorityType.ASC, Comparator.comparingInt(Listener::getPriority));
            // 调整降序队列
            doAddListener(nextDispatcher.desc, listener, PriorityType.DESC, (left, right) -> right.getPriority() - left.getPriority());

            return nextDispatcher;
        }

        // 执行新增监听器
        private void doAddListener(Map<Object, List<Listener>> eventTypeListenerses, Listener listener, PriorityType priorityType, Comparator<Listener> comparator) {
            for (Object eventType : listener.getEventTypes(priorityType)) {
                List<Listener> listeners = eventTypeListenerses.computeIfAbsent(eventType, k -> new ArrayList<>());
                listeners.add(listener);
                listeners.sort(comparator);
            }
        }

        // 删除监听器（返回新的分发器）
        Dispatcher removeListener(Listener listener) {
            Dispatcher nextDispatcher = copy();
            // 调整升序队列
            doRemoveListener(nextDispatcher.asc, listener, PriorityType.ASC);
            // 调整降序队列
            doRemoveListener(nextDispatcher.desc, listener, PriorityType.DESC);

            return nextDispatcher;
        }

        // 执行删除监听器
        private void doRemoveListener(Map<Object, List<Listener>> eventTypeListenerses, Listener listener, PriorityType priorityType) {
            for (Object eventType : listener.getEventTypes(priorityType)) {
                eventTypeListenerses.computeIfPresent(eventType, (k, listeners) -> {
                    listeners.remove(listener);
                    if (listeners.isEmpty()) {
                        listeners = null;
                    }
                    return listeners;
                });
            }
        }

        // 深度复制
        private Dispatcher copy() {
            Dispatcher newDispatcher = new Dispatcher();
            doCopy(asc, newDispatcher.asc);
            doCopy(desc, newDispatcher.desc);
            return newDispatcher;
        }

        // 执行深度复制
        private void doCopy(Map<Object, List<Listener>> source, Map<Object, List<Listener>> target) {
            source.forEach((eventType, listeners) -> {
                List<Listener> newListeners = new ArrayList<>(listeners.size() + 1);
                newListeners.addAll(listeners);
                target.put(eventType, newListeners);
            });
        }
    }
}
