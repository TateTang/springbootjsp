package com.springbootjsp.controller;

import com.springbootjsp.redis.OrderDelayedQueueService;
import com.springbootjsp.redis.OrderListener;
import com.springbootjsp.redis.TaskDTO;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/task")
public class DoSchedulerController {

    @Autowired
    private OrderDelayedQueueService orderDelayedQueue;

    @RequestMapping("/start")
    public Object doHiJob() throws SchedulerException {
//        StartScheduler.doHiJob();
//        CronScheduler.cronScheduler();
        run();
        return "OK";
    }

    public void run() {
        TaskDTO taskBody = new TaskDTO();
        taskBody.setBody("3秒之后执行");
        taskBody.setName("订单1");
        //添加队列3秒之后执行
        //redisDelayedQueue.addQueue(taskBody, 3, TimeUnit.SECONDS, TestListener.class.getName());

        taskBody.setBody("10秒之后执行");
        taskBody.setName("订单2");
        //添加队列10秒之后执行
        orderDelayedQueue.addQueue(taskBody, 10, TimeUnit.SECONDS, OrderListener.class.getName());
        //if(true) return;

        taskBody.setBody("20秒之后执行");
        taskBody.setName("订单3");
        //添加队列20秒之后执行
        orderDelayedQueue.addQueue(taskBody, 20, TimeUnit.SECONDS, OrderListener.class.getName());
        orderDelayedQueue.addQueue(taskBody, 21, TimeUnit.SECONDS, OrderListener.class.getName());
        orderDelayedQueue.addQueue(taskBody, 22, TimeUnit.SECONDS, OrderListener.class.getName());
        orderDelayedQueue.addQueue(taskBody, 23, TimeUnit.SECONDS, OrderListener.class.getName());
        orderDelayedQueue.addQueue(taskBody, 24, TimeUnit.SECONDS, OrderListener.class.getName());
        orderDelayedQueue.addQueue(taskBody, 25, TimeUnit.SECONDS, OrderListener.class.getName());

        taskBody.setBody("60秒之后执行");
        taskBody.setName("订单4");
        //添加队列20秒之后执行
        orderDelayedQueue.addQueue(taskBody, 60, TimeUnit.SECONDS, OrderListener.class.getName());

        taskBody.setBody("120秒之后执行");
        taskBody.setName("订单5");
        //添加队列20秒之后执行
        orderDelayedQueue.addQueue(taskBody, 120, TimeUnit.SECONDS, OrderListener.class.getName());
    }
}
