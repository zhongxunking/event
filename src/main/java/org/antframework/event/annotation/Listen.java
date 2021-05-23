/* 
 * 作者：钟勋 (email:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-09-29 12:53 创建
 */
package org.antframework.event.annotation;

import org.antframework.event.annotation.support.ClassListenResolver;
import org.antframework.event.listener.PriorityType;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 监听
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@org.antframework.event.annotation.listener.Listen(resolver = ClassListenResolver.class, priorityType = PriorityType.ASC)
public @interface Listen {
    /**
     * 优先级类型（默认为升序）
     */
    @AliasFor(annotation = org.antframework.event.annotation.listener.Listen.class, attribute = "priorityType")
    PriorityType priorityType() default PriorityType.ASC;
}
