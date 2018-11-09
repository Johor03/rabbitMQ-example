package com.example;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Component
public class DelayExchangeMessage {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @RabbitListener(queues = "queue.dlx")
    public void processDelay(User user, Message message, Channel channel) throws IOException {
        try {
            System.out.println("22开始执行延时消息处理......" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(new Date()) + ">>>             " + user);

            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);   //收到确认当前消息消息处理成功、false表示确认当前处理的这条消息/true表示确认当前及之前消息
            System.out.println("22延时消息处理完毕");

        } catch (Exception e) {
            System.out.println("22延时消息处理失败:{}"+e.getMessage());
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }

    //延时消息，通过死信队列
    public void  sendDataDelay(long delayTime){
        rabbitTemplate.convertAndSend("exchange.normal","",new User(1,"石头222","发送时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(new Date()), (short) 20),message -> {
            message.getMessageProperties().setExpiration(delayTime + "");
            message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            return message;
        });
    }
}