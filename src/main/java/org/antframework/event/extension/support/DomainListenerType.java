/* 
 * 作者：钟勋 (email:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-09-29 12:41 创建
 */
package org.antframework.event.extension.support;

import org.antframework.event.extension.EventTypeResolver;
import org.antframework.event.extension.ListenerType;

/**
 * 领域监听器类型
 */
public class DomainListenerType implements ListenerType {
    @Override
    public EventTypeResolver getResolver() {
        return ClassEventTypeResolver.INSTANCE;
    }
}
