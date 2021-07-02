package com.springbootjsp.redisDealyQueue;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RDelayedQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Author tangmf
 * @Date 2021/7/2 3:04 下午
 * @Description 订单延迟队列service服务类
 */
@Slf4j
@Component
public class OrderDelayedQueueService {

    @Autowired
    RDelayedQueue orderRDelayedQueue;

    /**
     * 添加队列
     *
     * @param t        DTO传输类
     * @param delay    时间数量
     * @param timeUnit 时间单位
     * @param <T>      泛型
     */
    public <T> void addQueue(T t, long delay, TimeUnit timeUnit, String queueName) {
        try {
            log.info("添加队列：{},延迟:{} {}执行", queueName, delay, timeUnit);
            orderRDelayedQueue.offer(t, delay, timeUnit);
        } catch (Exception e) {
            log.error("添加到延时队列失败：" + e.getMessage(), e);
            throw new RuntimeException("添加到延时队列失败");
        }
    }
}
