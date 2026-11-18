package com.example.websockets.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationController {

    @MessageMapping("/send")
    @SendTo("/topic/notifications")
    public String send(String message) {
        System.out.println("Sending message: " + message);
        return message;
    }


}
