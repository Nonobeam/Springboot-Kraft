package org.personal.client.web.controller;

import org.personal.client.web.core.Message;
import org.personal.client.web.service.SendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/")
@RestController
public class SendMessageController {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "chat-messages";

    @Autowired
    public SendMessageController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/send")
    public void sendMessage(@RequestBody String message) {
        System.out.println("Sending: " + message);
        kafkaTemplate.send(TOPIC, message);
    }

    @KafkaListener(topics = "chat-messages", groupId = "chat-client-group")
    public void listen(String message) {
        System.out.println("Client-Received: " + message);
    }
}
