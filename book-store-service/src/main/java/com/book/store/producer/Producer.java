package com.book.store.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import com.book.store.dto.EmailDetails;
import org.springframework.stereotype.Component;

@Component
public class Producer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${create.order.topic.notification}")
    private String createOrderTopic;

    public void sendOrderEmail(EmailDetails emailDetails){

        try {
            String stringJson = objectMapper.writeValueAsString(emailDetails);
            System.out.println("\nString JSON : " + stringJson);

            EmailDetails jsonObject = objectMapper.readValue(stringJson, EmailDetails.class);
            System.out.println("\nObject JSON : " + jsonObject);

            kafkaTemplate.send(createOrderTopic, stringJson);
        } catch (Exception e) {
            System.out.println("Error occurred : " + e.getMessage());
        }

    }
}
