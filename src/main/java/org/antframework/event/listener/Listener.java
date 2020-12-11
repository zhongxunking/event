/* 
 * 作者：钟勋 (email:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2020-12-11 17:41 创建
 */
package org.antframework.event.listener;

import java.util.Set;

/**
 * 监听器
 */
public interface Listener {
    /**
     * 获取类型
     *
     * @return 类型
     */
    Class<? extends ListenerType> getType();

    /**
     * 获取优先级
     *
     * @return 优先级
     */
    int getPriority();

    /**
     * 获取监听的所有事件类型
     *
     * @param priorityType 优先级类型
     * @return 所有事件类型
     */
    Set<Object> getEventTypes(PriorityType priorityType);

    /**
     * 执行
     *
     * @param event 事件
     * @throws Throwable 异常
     */
    void execute(Object event) throws Throwable;
}
