/* 
 * 作者：钟勋 (email:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-09-29 11:42 创建
 */
package org.antframework.event.listener;

/**
 * 数据类型（实现类需具有默认构造方法）
 */
public interface DataType {
    /**
     * 获取事件类型解决器
     */
    EventTypeResolver getResolver();

    /**
     * 事件类型解决器
     */
    interface EventTypeResolver {
        /**
         * 根据事件得到对应的事件类型
         *
         * @param event 事件
         * @return 事件类型
         */
        Object resolve(Object event);
    }
}
