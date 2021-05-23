/* 
 * 作者：钟勋 (email:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-09-29 12:41 创建
 */
package org.antframework.event.annotation.support;

import org.antframework.event.listener.DataType;

/**
 * 领域数据类型
 */
public class DomainDataType implements DataType {
    @Override
    public EventTypeResolver getResolver() {
        return ClassEventTypeResolver.INSTANCE;
    }
}
