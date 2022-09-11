package com.epam.finalproject.listener;


import com.epam.finalproject.model.entity.PasswordResetToken;
import com.epam.finalproject.model.entity.User;
import com.epam.finalproject.model.event.OnPasswordResetEvent;
import com.epam.finalproject.service.EmailService;
import com.epam.finalproject.service.PasswordResetTokenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * The type Password reset listener.
 * Lister to {@link OnPasswordResetEvent} event
 */
@Component
@Slf4j
@AllArgsConstructor
public class PasswordResetListener implements ApplicationListener<OnPasswordResetEvent> {

    private final PasswordResetTokenService passwordResetTokenService;

    private final EmailService emailService;

    /**
     * Performs creating password token and send email with link to perform password reset to target user from event object
     *
     * @param event the  {@link OnPasswordResetEvent} event object
     */
    @Override
    public void onApplicationEvent(@NonNull final OnPasswordResetEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(final OnPasswordResetEvent event) {
        final User user = event.getUser();
        PasswordResetToken token = passwordResetTokenService.createTokenForUser(user);
        log.info("Create for user {} verifyToken : {}", user.getUsername(), token);
        emailService.sendPasswordResetLetter(event, token);
    }

}
