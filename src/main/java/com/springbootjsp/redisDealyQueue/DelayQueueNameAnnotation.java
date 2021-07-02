package com.springbootjsp.redisDealyQueue;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author tangmf
 * @Date 2021/7/2 3:04 下午
 * @Description 延迟队列名称注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DelayQueueNameAnnotation {
    String name();
}
