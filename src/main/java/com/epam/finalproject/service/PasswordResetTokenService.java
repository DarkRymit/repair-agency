package com.epam.finalproject.service;

import com.epam.finalproject.model.entity.PasswordResetToken;
import com.epam.finalproject.model.entity.User;
import com.epam.finalproject.payload.request.NewPasswordRequest;

import java.time.Instant;
import java.util.Optional;
import java.util.function.Supplier;

public interface PasswordResetTokenService {
    PasswordResetToken createTokenForUser(User user);

    Optional<PasswordResetToken> findByToken(String token);

    boolean isExpired(PasswordResetToken passwordResetToken);

    boolean isExpired(PasswordResetToken passwordResetToken, Supplier<Instant> dateSupplier);

    void newPassword(PasswordResetToken passwordResetToken, NewPasswordRequest newPasswordRequest);
}
