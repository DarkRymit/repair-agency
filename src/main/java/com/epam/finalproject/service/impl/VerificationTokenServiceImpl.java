package com.epam.finalproject.service.impl;

import com.epam.finalproject.aop.logging.Loggable;
import com.epam.finalproject.model.entity.Role;
import com.epam.finalproject.model.entity.enums.RoleEnum;
import com.epam.finalproject.model.entity.User;
import com.epam.finalproject.model.entity.VerificationToken;
import com.epam.finalproject.repository.RoleRepository;
import com.epam.finalproject.repository.UserRepository;
import com.epam.finalproject.repository.VerificationTokenRepository;
import com.epam.finalproject.service.VerificationTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

@Service
@Slf4j
public class VerificationTokenServiceImpl implements VerificationTokenService {

    private final Integer expiration;

    VerificationTokenRepository verificationTokenRepository;

    UserRepository userRepository;

    RoleRepository roleRepository;

    public VerificationTokenServiceImpl(@Value("${token.verify.expiration}")Integer expiration,VerificationTokenRepository verificationTokenRepository, UserRepository userRepository, RoleRepository roleRepository) {
        this.expiration = expiration;
        this.verificationTokenRepository = verificationTokenRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    @Loggable
    public VerificationToken createTokenForUser(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = VerificationToken.builder()
                .token(token)
                .user(user)
                .expiryDate(calculateExpiryDate())
                .build();
        verificationTokenRepository.save(verificationToken);
        log.info("Create token :" + verificationToken);
        return verificationToken;
    }

    @Override
    @Loggable
    public Optional<VerificationToken> findByToken(String token) {
        return verificationTokenRepository.findByToken(token);
    }

    @Override
    @Loggable
    public boolean isExpired(VerificationToken verificationToken) {
        return isExpired(verificationToken, Instant::now);
    }

    @Override
    @Loggable
    public boolean isExpired(VerificationToken verificationToken, Supplier<Instant> dateSupplier) {
        return verificationToken.getExpiryDate().isBefore(dateSupplier.get());
    }

    @Override
    @Transactional
    @Loggable
    public void verifyByToken(VerificationToken token) {
        Role role = roleRepository.findByName(RoleEnum.UNVERIFIED).orElseThrow();
        User user = token.getUser();
        user.deleteRole(role);
        userRepository.save(user);
        verificationTokenRepository.delete(token);
    }

    private Instant calculateExpiryDate() {
        return Instant.now().plus(Duration.ofMinutes(expiration));
    }
}
