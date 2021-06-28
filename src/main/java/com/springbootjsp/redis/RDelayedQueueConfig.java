package com.springbootjsp.redis;

import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RDelayedQueueConfig {

    /**
     * 订单延迟队列
     * @param redissonClient
     * @return
     */
    @Bean
    public RDelayedQueue orderRDelayedQueue(RedissonClient redissonClient) {
        String name = OrderListener.class.getAnnotation(DelayQueueNameAnnotation.class).name();
        RBlockingQueue<Object> blockingFairQueue = redissonClient.getBlockingQueue(name == null ? OrderListener.class.getName() : name);
        return redissonClient.getDelayedQueue(blockingFairQueue);
    }
}
