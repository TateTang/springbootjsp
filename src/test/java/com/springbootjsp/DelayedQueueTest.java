package com.springbootjsp;

import com.springbootjsp.dealyQueueNew.RedisDelayedQueue;
import com.springbootjsp.redisDealyQueue.OrderDelayedQueueService;
import com.springbootjsp.redisDealyQueue.OrderListener;
import com.springbootjsp.redisDealyQueue.TaskDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

/**
 * @Author tangmf
 * @Date 2021/7/1 3:00 下午
 * @Description
 */
@Slf4j
public class DelayedQueueTest extends AppTest {
    @Autowired
    OrderDelayedQueueService orderDelayedQueue;
    @Autowired
    RedisDelayedQueue redisDelayedQueue;

    @Test
    public void test1() {
        //3秒后执行
        TaskDto taskBody = new TaskDto();
        taskBody.setBody("10秒之后执行");
        taskBody.setName("订单1");
        redisDelayedQueue.addQueue(taskBody, 10, TimeUnit.SECONDS, DelayedQueueTest.class.getName());//10秒后执行

        taskBody.setBody("20秒之后执行");
        taskBody.setName("订单2");
        redisDelayedQueue.addQueue(taskBody, 20, TimeUnit.SECONDS, DelayedQueueTest.class.getName());//20秒后执行

        taskBody.setBody("30秒之后执行");
        taskBody.setName("订单3");
        redisDelayedQueue.addQueue(taskBody, 30, TimeUnit.SECONDS, DelayedQueueTest.class.getName());//30秒后执行

        //监听延迟队列
        RedisDelayedQueue.TaskEventListener<TaskDto> taskEventListener = new RedisDelayedQueue.TaskEventListener<TaskDto>() {
            @Override
            public void invoke(TaskDto taskDTO) {
                //这里调用你延迟之后的代码
                log.info("执行...." + taskDTO.getBody() + "===" + taskDTO.getName());
            }
        };
        redisDelayedQueue.getQueue(TaskDto.class, taskEventListener);
    }

    @Test
    public void test2() {
        TaskDto taskBody = new TaskDto();

        //添加队列10秒之后执行
        taskBody.setBody("10秒之后执行");
        taskBody.setName("订单2");
        orderDelayedQueue.addQueue(taskBody, 10, TimeUnit.SECONDS, OrderListener.class.getName());

        //添加队列20秒之后执行
        taskBody.setBody("20秒之后执行");
        taskBody.setName("订单2");
        orderDelayedQueue.addQueue(taskBody, 20, TimeUnit.SECONDS, OrderListener.class.getName());

        //添加队列30秒之后执行
        taskBody.setBody("30秒之后执行");
        taskBody.setName("订单3");
        orderDelayedQueue.addQueue(taskBody, 30, TimeUnit.SECONDS, OrderListener.class.getName());
    }
}
