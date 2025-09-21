package com.test.kafka.controller;

import com.test.kafka.domain.message.producer.MessageProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageApiController {

    private final MessageProducer messageProducer;

    public MessageApiController(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    @PostMapping("/api/v1/messages")
    public ResponseEntity<String> sendMessage(@RequestParam String message) {
        messageProducer.sendMessage(message);
        return ResponseEntity.ok("SUCCESS");
    }

}
