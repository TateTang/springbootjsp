package com.springbootjsp.rabbit;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author tangmf
 * @Date 2021/6/24 6:11 下午
 * @Description 设置消费者监听
 */
@Component
@Slf4j
public class DeadLetterQueueConsumer {
    @RabbitListener(queues = DelayedRabbitMqConfig.DELAYED_QUEUE_NAME)
    public void receiveD(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody());
        log.info("当前时间：{},取消订单,msg:{}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), msg);
        /*
        channel.basicAck(long deliveryTag, boolean multiple); 确认消息
        deliveryTag	消息的随机标签信息
        multiple	是否批量；true表示一次性的将小于deliveryTag的值进行ack
         */
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);//表示启动消费方的手动确认        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);//确认消息已经被消费
    }
}
