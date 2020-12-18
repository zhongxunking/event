/* 
 * 作者：钟勋 (email:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2016-12-16 01:14 创建
 */
package org.antframework.event.annotation;

import org.antframework.event.annotation.listener.Listener;
import org.antframework.event.annotation.support.DomainDataType;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 领域监听器
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Listener(dataType = DomainDataType.class, priority = Integer.MAX_VALUE)
public @interface DomainListener {
    /**
     * 优先级
     */
    @AliasFor(annotation = Listener.class, attribute = "priority")
    int priority() default Integer.MAX_VALUE;
}
