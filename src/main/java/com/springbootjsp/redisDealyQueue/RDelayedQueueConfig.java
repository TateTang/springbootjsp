package com.springbootjsp.redisDealyQueue;

import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author tangmf
 * @Date 2021/7/2 3:04 下午
 * @Description 延迟队列配置
 */
@Configuration
public class RDelayedQueueConfig {

    /**
     * 订单延迟队列
     *
     * @param redissonClient redisson客户端
     */
    @Bean
    public RDelayedQueue orderRDelayedQueue(RedissonClient redissonClient) {
        String name = OrderListener.class.getAnnotation(DelayQueueNameAnnotation.class).name();
        RBlockingQueue<Object> blockingFairQueue = redissonClient.getBlockingQueue(name);
        return redissonClient.getDelayedQueue(blockingFairQueue);
    }
}
