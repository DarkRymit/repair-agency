package com.epam.finalproject.service;

import com.epam.finalproject.entity.User;
import com.epam.finalproject.entity.VerificationToken;

import java.util.Optional;

public interface VerificationTokenService {
    String createTokenForUser(User user);

    Optional<VerificationToken> findByToken(String token);

    boolean isExpired(VerificationToken verificationToken);

    void verifyByToken(VerificationToken token);
}
