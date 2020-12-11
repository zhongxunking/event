/* 
 * 作者：钟勋 (email:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2016-12-16 01:14 创建
 */
package org.antframework.event.annotation.listener.support;

import lombok.extern.slf4j.Slf4j;
import org.antframework.event.annotation.listener.Listen;
import org.antframework.event.annotation.listener.ListenResolver;
import org.antframework.event.annotation.listener.support.AnnotationListener.ListenExecutor;
import org.antframework.event.listener.Listener;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * 注解监听器解析器
 */
@Slf4j
public final class AnnotationListenerParser {
    /**
     * 解析监听器
     *
     * @param listener 监听器
     * @return 监听器
     */
    public static Listener parseListener(Object listener) {
        // 获取目标class（应对AOP代理情况）
        Class<?> listenerClass = AopUtils.getTargetClass(listener);
        log.debug("解析监听器：{}", listenerClass);
        org.antframework.event.annotation.listener.Listener listenerAnnotation = AnnotatedElementUtils.findMergedAnnotation(listenerClass, org.antframework.event.annotation.listener.Listener.class);
        // 解析
        Map<Object, ListenExecutor> eventTypeListenExecutors = parseListens(listenerClass);

        return new AnnotationListener(listenerAnnotation.type(), listenerAnnotation.priority(), listener, eventTypeListenExecutors);
    }

    // 解析所有监听方法
    private static Map<Object, ListenExecutor> parseListens(Class<?> listenerClass) {
        Map<Object, ListenExecutor> eventTypeListenExecutors = new HashMap<>();
        // 解析
        ReflectionUtils.doWithLocalMethods(listenerClass, method -> {
            Listen listenAnnotation = AnnotatedElementUtils.findMergedAnnotation(method, Listen.class);
            if (listenAnnotation != null) {
                ListenExecutor listenExecutor = parseListen(listenAnnotation, method);
                Assert.isTrue(!eventTypeListenExecutors.containsKey(listenExecutor.getEventType()), String.format("监听器[%s]存在监听同一个事件类型[%s]的多个方法", listenerClass, listenExecutor.getEventType()));
                eventTypeListenExecutors.put(listenExecutor.getEventType(), listenExecutor);
            }
        });

        return eventTypeListenExecutors;
    }

    // 解析监听方法
    private static ListenExecutor parseListen(Listen listenAnnotation, Method listenMethod) {
        log.debug("解析监听方法：{}", listenMethod);
        // 校验方法类型
        Assert.isTrue(Modifier.isPublic(listenMethod.getModifiers()), String.format("监听方法[%s]必须是public类型", listenMethod));
        // 创建监听解决器
        ListenResolver resolver = BeanUtils.instantiate(listenAnnotation.resolver());
        resolver.init(listenMethod);

        return new ListenExecutor(resolver, listenAnnotation.priorityType(), listenMethod);
    }
}
