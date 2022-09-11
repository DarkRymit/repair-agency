package com.epam.finalproject.service;

import com.epam.finalproject.model.entity.PasswordResetToken;
import com.epam.finalproject.model.entity.VerificationToken;
import com.epam.finalproject.model.event.OnPasswordResetEvent;
import com.epam.finalproject.model.event.OnRegistrationCompleteEvent;
import org.springframework.mail.MailException;

import javax.mail.internet.MimeMessage;

public interface EmailService {

    MimeMessage createMimeMessage() throws MailException;

    void send(MimeMessage message) throws MailException;


    void sendVerificationLetter(OnRegistrationCompleteEvent event, VerificationToken token) throws MailException;

    void sendPasswordResetLetter(OnPasswordResetEvent event, PasswordResetToken token) throws MailException;
}
