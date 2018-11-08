package com.example;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DelayConsumer {

    @RabbitHandler
    @RabbitListener(queues = RabbitMqConfig.Delay_Notify_Queue_Name)
    public void process(@Payload User data,Message message, Channel channel) throws IOException {
        try {
            System.out.println("开始执行延时消息处理......"+ data);
            //int i = 1/0;
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);   //收到确认当前消息消息处理成功、false表示确认当前处理的这条消息/true表示确认当前及之前消息
            System.out.println("延时消息处理完毕");

        } catch (Exception e) {
            System.out.println("延时消息处理失败:{}"+e.getMessage());
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        } 
    }

}