/* 
 * 作者：钟勋 (email:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-09-29 11:51 创建
 */
package org.antframework.event.annotation.support;


import org.antframework.event.listener.DataType;

/**
 * Class事件类型解决器（事件类型就是事件对应的Class类）
 */
public class ClassEventTypeResolver implements DataType.EventTypeResolver {
    /**
     * 实例
     */
    public static final ClassEventTypeResolver INSTANCE = new ClassEventTypeResolver();

    @Override
    public Object resolve(Object event) {
        return event.getClass();
    }
}
