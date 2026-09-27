package web.minda.project.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import web.minda.project.dto.UserCreatedEvent;

@Service
public class UserEventConsumer {

    private final EmailService emailService;
    private final ActivationService activationService;

    public UserEventConsumer(
            EmailService emailService,
            ActivationService activationService) {

        this.emailService = emailService;
        this.activationService = activationService;
    }

    @KafkaListener(
        topics = "user-created",
        groupId = "notification-service"
    )
    public void consumeUserCreated(UserCreatedEvent event) {

        System.out.println(
            "User Created: " + event.getEmail()
        );

        String activationToken =
                activationService.createActivationToken(
                        event.getEmail()
                );

        emailService.sendWelcomeEmail(
                event.getEmail(),
                event.getFirstName(),
                activationToken
        );
    }
}