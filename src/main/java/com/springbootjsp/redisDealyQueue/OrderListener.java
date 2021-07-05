package com.springbootjsp.redisDealyQueue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @Author tangmf
 * @Date 2021/7/2 3:04 下午
 * @Description 订单支付超时监听器 执行业务代码
 */
@Slf4j
@Component
@DelayQueueNameAnnotation(name = "order_delay_queue")//加上注解，定义延迟队列名称
public class OrderListener implements RDelayedQueueListener<TaskDto> {

    @Override
    public void invoke(TaskDto taskDto) {
        log.info("执行....{}，参数：name{}，body：{}", taskDto,
                taskDto.getName(), taskDto.getBody());
    }
}
