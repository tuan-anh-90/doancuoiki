package com.project.urban.Service;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiice {
	@Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String recipientEmail, String link)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("truongan190502@shopme.com", "Reset Password");
        helper.setTo(recipientEmail);

        String subject = "Here is the link for you to reset the password for your account";

        String content = "<p>Xin chào,</p>"
                + "<p>You have just sent a request to reset your account password</p>"
                + "<p>Click on the link below to go to the new password entry page:</p>"
                + "<p><a href=\"" + link + "\">Reset Password</a></p>"
                + "<br>"
                + "<p>Ignore this Email if you already remember your old password</p>";

        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(message);
    }
}
