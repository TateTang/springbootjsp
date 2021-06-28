package com.springbootjsp.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author tangmf
 * @Date 2021/6/24 6:13 下午
 * @Description
 */
@Slf4j
@RequestMapping("rabbitmq")
@RestController
public class RabbitMQMsgController {

    @Autowired
    private DelayMessageSender sender;


    /**
     * 发送延迟取消订单消息
     *
     * @param msg       消息体
     * @param delayTime 自定义延迟取消订单时间（毫秒）
     */
    @RequestMapping("delayMsg")
    public void delayMsg(String msg, Integer delayTime) {
        msg = msg + "=>" + (int) (Math.random() * 90000.0D + 10000.0D);
        log.info("当前时间：{},生成订单,msg:{},delayTime:{}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), msg, delayTime);
        sender.sendDelayMsg(msg, delayTime);
    }
}
