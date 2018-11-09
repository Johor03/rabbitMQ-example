package com.example.delayplugin;

import com.example.config.RabbitMqConfig;
import com.example.User;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 通过rabbitmq 延时队列插件实现延时消息发送
 */
@Component
public class DelayPluginMessage {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitHandler
    @RabbitListener(queues = RabbitMqConfig.Delay_Notify_Queue_Name)
    public void process(@Payload User data, Message message, Channel channel) throws IOException {
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

    /**
     * 发送延时消息
     * @param obj
     */
    public void delaySend(Object obj,Integer delayTime){
        // 通过广播模式发布延时消息 延时2分钟 持久化消息 消费后销毁 这里无需指定路由，会广播至每个绑定此交换机的队列
        rabbitTemplate.convertAndSend(RabbitMqConfig.Delay_Exchange_Name, "", obj, message ->{
            message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            message.getMessageProperties().setDelay(delayTime);   // 毫秒为单位，指定此消息的延时时长
            return message;
        });
    }

}