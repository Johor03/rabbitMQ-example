package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/rabbit")
public class RabbitMQController {

    @Autowired
    private SenderMQ sender;

    @Autowired
    private DelayPluginMessage delayConsumer;
    @Autowired
    private DelayExchangeMessage delayExchangeMessage;

    @GetMapping()
    public String Main(){
        return "<h1>hello RabbitMQ!</h1>";
    }

    /**
     * 发送普通异步mq消息
     * @return
     * @throws Exception
     */
    @GetMapping("/send")
    public String Send() throws Exception{
        sender.sendData();
        return "Send OK！";
    }

    /**
     * rabbitMQ插件实现延时队列
     * @return
     * @throws Exception
     */
    @GetMapping("/delay-plugin")
    public String SendDelay() throws Exception{
        delayConsumer.delaySend(new User(1,"石头", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(new Date()), (short) 20),1000*60*2);
        return "Send OK！";
    }

    /**
     * rabbitMQ死信队列实现延时队列
     * @return
     * @throws Exception
     */
    @GetMapping("/delay-exchange")
    public String SendDelay2() throws Exception{
        delayExchangeMessage.sendDataDelay(1000*60*2);
        return "Send OK！";
    }
}