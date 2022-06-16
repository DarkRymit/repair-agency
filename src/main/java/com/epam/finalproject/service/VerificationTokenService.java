package com.epam.finalproject.service;

import com.epam.finalproject.entity.User;
import com.epam.finalproject.entity.VerificationToken;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Supplier;

public interface VerificationTokenService {
    VerificationToken createTokenForUser(User user);

    Optional<VerificationToken> findByToken(String token);

    boolean isExpired(VerificationToken verificationToken);

    boolean isExpired(VerificationToken verificationToken, Supplier<LocalDateTime> dateSupplier);

    void verifyByToken(VerificationToken token);
}
