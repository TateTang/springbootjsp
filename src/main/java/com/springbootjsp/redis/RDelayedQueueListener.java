package com.springbootjsp.redis;

/**
 * 事件监听接口
 */
public interface RDelayedQueueListener<T> {
    void invoke(T t);
}