package com.book.store.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public String publishMessage(String message) {
        kafkaTemplate.send("my-topic", message);
        return "Message sent: " + message;
    }

}
