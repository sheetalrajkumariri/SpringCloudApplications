package notifications.service;

import notifications.dto.EmailDetails;

public interface EmailService {
    String sendSimpleMail(EmailDetails details);
}
