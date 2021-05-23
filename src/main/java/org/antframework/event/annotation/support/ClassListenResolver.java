/* 
 * 作者：钟勋 (email:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-09-29 11:54 创建
 */
package org.antframework.event.annotation.support;

import org.antframework.event.annotation.listener.ListenResolver;
import org.springframework.util.Assert;

import java.lang.reflect.Method;

/**
 * Class监听解决器（监听方法只能有一个入参，事件类型就是入参的Class类）
 */
public class ClassListenResolver implements ListenResolver {
    // 监听的事件类型
    private Class eventType;

    @Override
    public void init(Method listenMethod) {
        // 校验入参
        Class[] parameterTypes = listenMethod.getParameterTypes();
        Assert.isTrue(parameterTypes.length == 1, String.format("监听方法[%s]必须只有一个入参", listenMethod));
        // 设置事件类型
        eventType = parameterTypes[0];
    }

    @Override
    public Object getEventType() {
        return eventType;
    }

    @Override
    public Object[] resolveParams(Object event) {
        return new Object[]{event};
    }
}
