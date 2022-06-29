package com.epam.finalproject.listener;


import com.epam.finalproject.model.entity.PasswordResetToken;
import com.epam.finalproject.model.entity.User;
import com.epam.finalproject.model.event.OnPasswordResetEvent;
import com.epam.finalproject.service.PasswordResetTokenService;
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
