package com.springbootjsp.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 订单支付超时监听器
 * 执行业务代码
 */
@Slf4j
@Component
@DelayQueueNameAnnotation(name="order_delay_queue1")
public class OrderListener implements RDelayedQueueListener<TaskDTO> {

    @Override
    public void invoke(TaskDTO taskDTO) {
        log.info("执行....{}", taskDTO);
    }
}
