package com.epam.finalproject.task;


import com.epam.finalproject.repository.PasswordResetTokenRepository;
import com.epam.finalproject.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;

@Service
@AllArgsConstructor
@Transactional
public class TokensPurgeTask {

    VerificationTokenRepository verificationTokenRepository;

    PasswordResetTokenRepository passwordTokenRepository;

    @Scheduled(cron = "${purge.cron.expression}")
    public void purgeExpired() {

        Date now = Date.from(Instant.now());

        passwordTokenRepository.deleteAllExpiredSince(now);
        verificationTokenRepository.deleteAllExpiredSince(now);
    }
}
