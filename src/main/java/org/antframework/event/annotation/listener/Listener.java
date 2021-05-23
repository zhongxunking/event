/* 
 * 作者：钟勋 (email:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2016-12-16 01:14 创建
 */
package org.antframework.event.annotation.listener;

import org.antframework.event.listener.DataType;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 监听器
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Listener {
    /**
     * 数据类型
     */
    Class<? extends DataType> dataType();

    /**
     * 优先级
     */
    int priority();
}
