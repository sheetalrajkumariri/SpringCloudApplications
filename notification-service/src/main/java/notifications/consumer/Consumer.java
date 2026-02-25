package notifications.consumer;

import notifications.dto.EmailDetails;
import notifications.service.EmailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {
    @Autowired
    private EmailService emailService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "create-order-notification", groupId = "group_id")
    public void consume(String message) {
        System.out.println("\nMessage received: " + message);
        try {
            EmailDetails emailDetails = objectMapper.readValue(message, EmailDetails.class);

            emailService.sendSimpleMail(emailDetails);

        } catch (Exception e) {
            System.out.println("Error while consuming message: " + e.getMessage());
        }

    }

}
