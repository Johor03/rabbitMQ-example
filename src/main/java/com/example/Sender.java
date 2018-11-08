package com.example;

import com.rabbitmq.client.MessageProperties;
import lombok.Data;
import lombok.Value;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Component
public class Sender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    //发送一般消息,
    public void sendData(){
        rabbitTemplate.convertAndSend("exchange-notify","unicode-order",new User(1,"石头","北京市朝阳区", (short) 20));
    }

    /**
     * 发送延时消息
     * @param obj
     */
    public void delaySend(Object obj){
        // 通过广播模式发布延时消息 延时2分钟 持久化消息 消费后销毁 这里无需指定路由，会广播至每个绑定此交换机的队列
        rabbitTemplate.convertAndSend(RabbitMqConfig.Delay_Exchange_Name, "", obj, message ->{
            message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            message.getMessageProperties().setDelay(2*60*1000);   // 毫秒为单位，指定此消息的延时时长
            return message;
        });
    }

}


