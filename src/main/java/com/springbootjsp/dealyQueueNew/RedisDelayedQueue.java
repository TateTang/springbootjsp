package com.springbootjsp.dealyQueueNew;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Author tangmf
 * @Date 2021/7/1 2:53 下午
 * @Description
 */
@Component
@Slf4j
public class RedisDelayedQueue {
    /**
     * 任务回调监听
     *
     * @param <T>
     */
    public abstract static class TaskEventListener<T> {
        /**
         * 执行方法
         *
         * @param t
         */
        public abstract void invoke(T t);
    }

    @Resource
    RedissonClient redissonClient;

    /**
     * 添加队列
     *
     * @param t        DTO传输类
     * @param delay    时间数量
     * @param timeUnit 时间单位
     * @param <T>      泛型
     */
    public <T> void addQueue(T t, long delay, TimeUnit timeUnit, String queueName) {
        RBlockingQueue<T> blockingFairQueue = redissonClient.getBlockingQueue(t.getClass().getName());
        RDelayedQueue<T> delayedQueue = redissonClient.getDelayedQueue(blockingFairQueue);
        log.info("添加队列：{}，延迟:{} {}执行", queueName, delay, timeUnit);
        delayedQueue.offer(t, delay, timeUnit);
        delayedQueue.destroy();
    }

    /**
     * 获取队列
     *
     * @param zClass            DTO泛型
     * @param taskEventListener 任务回调监听
     * @param <T>               泛型
     */
    public <T> void getQueue(Class<T> zClass, TaskEventListener<T> taskEventListener) {
        RBlockingQueue<T> blockingFairQueue = redissonClient.getBlockingQueue(zClass.getName());
        //由于此线程需要常驻，可以新建线程，不用交给线程池管理
        ((Runnable) () -> {
            while (true) {
                try {
                    T t = blockingFairQueue.take();
                    taskEventListener.invoke(t);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).run();
        //
        //ExecutorService service = Executors.newFixedThreadPool(10);
        //service.execute(new Thread(() -> {
        //    while (true) {
        //        try {
        //            T t = blockingFairQueue.take();
        //            //启动一个任务回调监听线程
        //            new Thread(() -> taskEventListener.invoke(t);
        //        } catch (InterruptedException e) {
        //            e.printStackTrace();
        //        }
        //    }
        //}));
        //service.shutdown();//完成执行
    }
}


