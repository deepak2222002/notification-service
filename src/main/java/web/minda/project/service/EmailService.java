package web.minda.project.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;

	@Value("${app.activation-url}")
	private String activationUrl;

	public void sendWelcomeEmail(String email, String name, String activationToken) {

		try {

//			String activationToken = UUID.randomUUID().toString();

			MimeMessage message = mailSender.createMimeMessage();

			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

			helper.setTo(email);

			helper.setSubject("Activate your Job Portal account");

			String activationLink = activationUrl + "?token=" + activationToken;

			String htmlContent = "<!DOCTYPE html>" + "<html>" + "<head>" + "<meta charset='UTF-8'>"
					+ "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" + "</head>" +

					"<body style='margin:0; padding:0; background-color:#f4f6f8; font-family:Arial, sans-serif;'>" +

					"<div style='padding:40px 15px;'>" +

					"<div style='max-width:600px; margin:auto; background:#ffffff; "
					+ "border-radius:10px; padding:40px; " + "box-shadow:0 2px 10px rgba(0,0,0,0.08);'>" +

					"<h2 style='margin-top:0; color:#1f2937;'>" + "Welcome to Job Portal!" + "</h2>" +

					"<p style='font-size:16px; color:#374151;'>Hi " + name + ",</p>" +

					"<p style='font-size:15px; line-height:1.6; color:#4b5563;'>"
					+ "Your account has been successfully created. "
					+ "To complete your registration and start using your account, "
					+ "please verify your email address." + "</p>" +

					"<div style='text-align:center; margin:35px 0;'>" +
					
					"<a href='" + activationLink + "' style='display:inline-block; padding:14px 28px; >"
					+ "background-color:#2563eb; color:#ffffff; " + "text-decoration:none; border-radius:6px; "
					+ "font-size:15px; font-weight:bold;'>" +

					"Activate My Account" +

					"</a>" +

					"</div>" +

					"<p style='font-size:14px; color:#6b7280; line-height:1.6;'>"
					+ "For your security, this activation link will expire in " + "<strong>24 hours</strong>." + "</p>"
					+

					"<p style='font-size:14px; color:#6b7280; line-height:1.6;'>"
					+ "If you did not create a Job Portal account, " + "you can safely ignore this email." + "</p>" +

					"<hr style='border:none; border-top:1px solid #e5e7eb; margin:30px 0;'>" +

					"<p style='font-size:12px; color:#9ca3af; line-height:1.5;'>"
					+ "If the button above does not work, copy and paste this link " + "into your browser:<br><br>" +

					"<span style='word-break:break-all;'>" + activationLink + "</span>" +

					"</p>" +

					"<p style='font-size:13px; color:#6b7280; margin-top:30px;'>" + "Regards,<br>"
					+ "<strong>Job Portal Team</strong>" + "</p>" +

					"<p style='font-size:11px; color:#9ca3af; text-align:center; margin-top:35px;'>"
					+ "This is an automated message. Please do not reply to this email.<br>"
					+ "© 2026 Job Portal. All rights reserved." + "</p>" +

					"</div>" + "</div>" +

					"</body>" + "</html>";

			helper.setText(htmlContent, true);

			mailSender.send(message);

			System.out.println("Activation email sent to: " + email);

		} catch (MessagingException e) {

			throw new RuntimeException("Failed to send activation email to: " + email, e);
		}
	}
}