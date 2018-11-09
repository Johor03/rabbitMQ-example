package com.example.normal;

import com.example.config.RabbitMqConfig;
import com.example.User;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class NormalMQ {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    //发送一般消息
    public void sendData(){
        rabbitTemplate.convertAndSend("exchange-notify","unicode-order",new User(1,"石头","北京市朝阳区", (short) 20));
    }

    @RabbitHandler
    @RabbitListener(queues = RabbitMqConfig.ORDER_QUEUE)
    public void process(@Payload User message, @Headers Map<String,Object> headers) {
        System.out.println("Receiver Body : " + message);
    }

}


