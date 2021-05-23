/* 
 * 作者：钟勋 (email:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2020-12-11 18:40 创建
 */
package org.antframework.event.bus;

import org.antframework.event.listener.DataType;
import org.antframework.event.listener.Listener;

/**
 * 事件总线
 */
public interface EventBus {
    /**
     * 获取数据类型
     *
     * @return 数据类型
     */
    Class<? extends DataType> getDataType();

    /**
     * 新增监听器
     *
     * @param listener 监听器
     */
    void addListener(Listener listener);

    /**
     * 删除监听器
     *
     * @param listener 监听器
     */
    void removeListener(Listener listener);

    /**
     * 分发事件
     *
     * @param event 事件
     * @throws Throwable 异常
     */
    void dispatch(Object event) throws Throwable;
}
