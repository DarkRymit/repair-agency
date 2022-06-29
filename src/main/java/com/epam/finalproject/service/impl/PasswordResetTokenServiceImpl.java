package com.epam.finalproject.service.impl;

import com.epam.finalproject.entity.PasswordResetToken;
import com.epam.finalproject.entity.User;
import com.epam.finalproject.payload.request.NewPasswordRequest;
import com.epam.finalproject.repository.PasswordResetTokenRepository;
import com.epam.finalproject.repository.RoleRepository;
import com.epam.finalproject.repository.UserRepository;
import com.epam.finalproject.repository.VerificationTokenRepository;
import com.epam.finalproject.service.PasswordResetTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

@Service
@Slf4j
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

    private final Integer expiration;

    PasswordResetTokenRepository passwordResetTokenRepository;

    UserRepository userRepository;

    PasswordEncoder passwordEncoder;

    public PasswordResetTokenServiceImpl(@Value("${token.verify.expiration}") Integer expiration, PasswordResetTokenRepository passwordResetTokenRepository, UserRepository userRepository,PasswordEncoder passwordEncoder) {
        this.expiration = expiration;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public PasswordResetToken createTokenForUser(User user) {
                String token = UUID.randomUUID().toString();
        PasswordResetToken passwordResetToken = PasswordResetToken.builder()
                .token(token)
                .user(user)
                .expiryDate(calculateExpiryDate())
                .build();
        passwordResetTokenRepository.save(passwordResetToken);
        log.info("Create token :" + passwordResetToken);
        return passwordResetToken;
    }

    @Override
    public Optional<PasswordResetToken> findByToken(String token) {
        return passwordResetTokenRepository.findByToken(token);
    }

    @Override
    public boolean isExpired(PasswordResetToken passwordResetToken) {
        return isExpired(passwordResetToken, LocalDateTime::now);
    }

    @Override
    public boolean isExpired(PasswordResetToken passwordResetToken, Supplier<LocalDateTime> dateSupplier) {
        return passwordResetToken.getExpiryDate().isBefore(dateSupplier.get());
    }

    @Override
    @Transactional
    public void newPassword(PasswordResetToken token, NewPasswordRequest newPasswordRequest) {
        User user = token.getUser();
        user.setPassword(passwordEncoder.encode(newPasswordRequest.getPassword()));
        userRepository.save(user);
        passwordResetTokenRepository.delete(token);
    }
    private LocalDateTime calculateExpiryDate() {
        return LocalDateTime.now().plusMinutes(expiration);
    }
}
