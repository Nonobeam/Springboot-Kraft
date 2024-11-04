package org.personal.server.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/")
@RestController
public class ReceiveMessageController {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "chat-messages";

    @Autowired
    public ReceiveMessageController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "chat-messages", groupId = "chat-server-group")
    public void listen(String message) {
        System.out.println("Server received: " + message);
    }

    public void sendMessage(String message) {
        kafkaTemplate.send(TOPIC, message);
    }
}

