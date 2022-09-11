package com.epam.finalproject.service.impl;


import com.epam.finalproject.model.entity.PasswordResetToken;
import com.epam.finalproject.model.entity.VerificationToken;
import com.epam.finalproject.model.event.OnPasswordResetEvent;
import com.epam.finalproject.model.event.OnRegistrationCompleteEvent;
import com.epam.finalproject.service.EmailService;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class SpringEmailService implements EmailService {

    private final JavaMailSender sender;

    private final Properties properties;

    public SpringEmailService(JavaMailSender sender,@Qualifier("mailProperties") Properties properties) {
        this.sender = sender;
        this.properties = properties;
    }

    @Override
    public MimeMessage createMimeMessage() {
        return sender.createMimeMessage();
    }

    @Override
    public void send(MimeMessage message) throws MailException {
        sender.send(message);
    }

    @Override
    public void sendVerificationLetter(OnRegistrationCompleteEvent event,
            VerificationToken token) throws MailException {
        try {
            MimeMessage message = createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setTo(event.getUser().getEmail());
            message.setSubject("Verification");
            message.setText(properties.getProperty("mail.appUrl") + "/auth/confirmRegister/" + token.getToken());
            send(message);
        } catch (MessagingException e) {
            throw new MailException("Cant send verification letter", e) {};
        }
    }

    @Override
    public void sendPasswordResetLetter(OnPasswordResetEvent event, PasswordResetToken token) throws MailException {
        try {
            MimeMessage message = createMimeMessage();
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(event.getUser().getEmail()));
            message.setSubject("Password reset");
            message.setText(properties.getProperty("mail.appUrl") + "/auth/resetpassword/confirm/" + token.getToken());
            send(message);
        } catch (MessagingException e) {
            throw new MailException("Cant send password reset letter", e) {};
        }
    }


}
