package web.minda.project.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import web.minda.project.dto.UserCreatedEvent;

@Service
public class UserEventConsumer {

    private final EmailService emailService;

    public UserEventConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(
        topics = "user-created",
        groupId = "notification-service"
    )
    public void consumeUserCreated(UserCreatedEvent event) {

        System.out.println(
            "User Created: " + event.getEmail()
        );

        emailService.sendWelcomeEmail(
            event.getEmail(),
            event.getFirstName()
        );
    }
}