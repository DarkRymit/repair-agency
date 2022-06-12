package com.epam.finalproject.registration.listener;


import com.epam.finalproject.entity.User;
import com.epam.finalproject.registration.OnRegistrationCompleteEvent;
import com.epam.finalproject.service.VerificationTokenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    VerificationTokenService verificationTokenService;

    @Override
    public void onApplicationEvent(final OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(final OnRegistrationCompleteEvent event) {
        final User user = event.getUser();
        String token = verificationTokenService.createTokenForUser(user);
        log.info("Create for user " + user.getUsername() + " verifyToken : " + token);
    }

}
