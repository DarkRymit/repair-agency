package com.epam.finalproject.listener;


import com.epam.finalproject.model.entity.User;
import com.epam.finalproject.model.entity.VerificationToken;
import com.epam.finalproject.model.event.OnRegistrationCompleteEvent;
import com.epam.finalproject.service.EmailService;
import com.epam.finalproject.service.VerificationTokenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * The type Registration listener.
 * Lister to {@link OnRegistrationCompleteEvent} event
 */
@Component
@Slf4j
@AllArgsConstructor
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    private final VerificationTokenService verificationTokenService;

    private final EmailService emailService;


    /**
     * Performs creating verification token and send email with link to perform a verification of email to target user from event object
     *
     * @param event the  {@link OnRegistrationCompleteEvent} event object
     */
    @Override
    public void onApplicationEvent(@NonNull final OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(final OnRegistrationCompleteEvent event) {
        final User user = event.getUser();
        VerificationToken token = verificationTokenService.createTokenForUser(user);
        log.info("Create for user {} verifyToken : {}", user.getUsername(), token);
        emailService.sendVerificationLetter(event, token);
    }

}
