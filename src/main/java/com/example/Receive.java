package com.example;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Receive {


    @RabbitHandler
    @RabbitListener(queues = RabbitMqConfig.ORDER_QUEUE)
    public void process(@Payload User message, @Headers Map<String,Object> headers) {
        System.out.println("Receiver Body : " + message);
    }
}