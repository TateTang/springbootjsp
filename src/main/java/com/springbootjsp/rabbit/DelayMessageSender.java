package com.springbootjsp.rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author tangmf
 * @Date 2021/6/24 6:08 下午
 * @Description 生产者发送消息
 */
@Component
public class DelayMessageSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendDelayMsg(String msg, Integer delayTime) {
        rabbitTemplate.convertAndSend(DelayedRabbitMqConfig.DELAYED_EXCHANGE_NAME,
                DelayedRabbitMqConfig.DELAYED_ROUTING_KEY, msg, a -> {
                    a.getMessageProperties().setDelay(delayTime);
                    return a;
                });
    }
}
