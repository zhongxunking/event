/* 
 * 作者：钟勋 (email:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2021-02-15 13:36 创建
 */
package org.antframework.event.bus.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.antframework.event.listener.DataType;

/**
 * 事件上下文
 */
@AllArgsConstructor
@Getter
public class EventContext {
    // 数据类型
    private final Class<? extends DataType> dataType;
    // 事件
    private final Object event;
}
