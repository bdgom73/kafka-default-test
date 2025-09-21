package com.test.kafka.domain.message.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageConsumer {

    @KafkaListener(topics = "send-message-topic", groupId = "my-group")
    public void listen(String message) {
        System.out.println("send-message-topic Try: " + message);

        if (Math.random() < 0.7) {
            System.out.println("💥 send-message-topic FAILED to process message.");
            throw new RuntimeException("Simulated processing error!");
        }

        System.out.println("✅ send-message-topic Received message: " + message);
    }

}
