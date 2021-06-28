package com.springbootjsp.redis;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 初始化队列监听
 */

@Component
@Slf4j
public class RedisDelayedQueueInit implements ApplicationContextAware {

    @Autowired
    RedissonClient redissonClient;

    /**
     * 获得RDelayedQueueListener的实现类
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, RDelayedQueueListener> map = applicationContext.getBeansOfType(RDelayedQueueListener.class);
        for (Map.Entry<String, RDelayedQueueListener> taskEventListenerEntry : map.entrySet()) {
            /*是否使用bean的名字作为队列的名字*/
            boolean useBeanName = true;
            String listenerName = taskEventListenerEntry.getValue().getClass().getName();
            for(Annotation anno : taskEventListenerEntry.getValue().getClass().getAnnotations()){
                if(anno.annotationType().equals(DelayQueueNameAnnotation.class)){
                    String queueName = ((DelayQueueNameAnnotation) anno).name();
                    if(queueName != null){
                        /*使用了注解上的名字作为队列名*/
                        useBeanName = false;
                        startThread(queueName, taskEventListenerEntry.getValue());
                        break;
                    }
                }
            }
            if(useBeanName){
                /*使用全类名作为队列名字*/
                startThread(listenerName, taskEventListenerEntry.getValue());
            }
        }
    }

    /**
     * 启动线程获取队列*
     *
     * @param queueName                 queueName
     * @param redisDelayedQueueListener 任务回调监听
     * @param <T>                       泛型
     * @return
     */
    private <T> void startThread(String queueName, RDelayedQueueListener redisDelayedQueueListener) {
        RBlockingQueue<T> blockingQueue = redissonClient.getBlockingQueue(queueName);

        //***************************以下逻辑解决重启服务不消费问题****************
        RDelayedQueue<T> delayedQueue = redissonClient.getDelayedQueue(blockingQueue);
        TaskDTO taskBody = new TaskDTO();
        taskBody.setBody("test");
        taskBody.setName("test");
        delayedQueue.offer((T)taskBody, 1, TimeUnit.SECONDS);
        //*********************************************************************

        Thread thread = new Thread(() -> {
            log.info("启动监听队列线程" + queueName);
            while (true) {
                try {
                    T t = blockingQueue.take();
                    log.info("监听队列线程{},获取到值:{}", queueName, JSON.toJSONString(t));
                    new Thread(() -> {
                        redisDelayedQueueListener.invoke(t);
                    }).start();
                } catch (Exception e) {
                    log.info("监听队列线程错误,", e);
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException ex) {
                    }
                }
            }
        });
        thread.setName(queueName);
        thread.start();
    }

}