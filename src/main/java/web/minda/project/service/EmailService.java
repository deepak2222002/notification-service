package web.minda.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendWelcomeEmail(String email, String name) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);
        message.setSubject("Welcome to Our Application");

        message.setText(
            "Hello " + name + ",\n\n" +
            "Your account has been successfully created.\n\n" +
            "Welcome!"
        );

        mailSender.send(message);

        System.out.println("Welcome email sent to: " + email);
    }
}