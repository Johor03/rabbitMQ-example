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
    private Sender sender;


    @GetMapping()
    public String Main(){
        return "<h1>hello RabbitMQ!</h1>";
    }

    @GetMapping("/send")
    public String Send() throws Exception{
        sender.sendData();
        return "Send OK！";
    }


    @GetMapping("/delay")
    public String SendDelay() throws Exception{
        sender.delaySend(new User(1,"石头", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(new Date()), (short) 20));
        return "Send OK！";
    }
}