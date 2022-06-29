package com.epam.finalproject.resetpassword.listener;


import com.epam.finalproject.entity.PasswordResetToken;
import com.epam.finalproject.entity.User;
import com.epam.finalproject.entity.VerificationToken;
import com.epam.finalproject.registration.OnRegistrationCompleteEvent;
import com.epam.finalproject.resetpassword.OnPasswordResetEvent;
import com.epam.finalproject.service.PasswordResetTokenService;
import com.epam.finalproject.service.VerificationTokenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class PasswordResetListener implements ApplicationListener<OnPasswordResetEvent> {

    PasswordResetTokenService passwordResetTokenService;

    @Override
    public void onApplicationEvent(final OnPasswordResetEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(final OnPasswordResetEvent event) {
        final User user = event.getUser();
        PasswordResetToken token = passwordResetTokenService.createTokenForUser(user);
        log.info("Create for user " + user.getUsername() + " passwordResetToken : " + token);
    }

}
