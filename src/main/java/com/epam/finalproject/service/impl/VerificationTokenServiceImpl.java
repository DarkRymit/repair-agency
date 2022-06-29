package com.epam.finalproject.service.impl;

import com.epam.finalproject.model.entity.Role;
import com.epam.finalproject.model.entity.RoleEnum;
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
    public Optional<VerificationToken> findByToken(String token) {
        return verificationTokenRepository.findByToken(token);
    }

    @Override
    public boolean isExpired(VerificationToken verificationToken) {
        return isExpired(verificationToken, LocalDateTime::now);
    }

    @Override
    public boolean isExpired(VerificationToken verificationToken, Supplier<LocalDateTime> dateSupplier) {
        return verificationToken.getExpiryDate().isBefore(dateSupplier.get());
    }

    @Override
    @Transactional
    public void verifyByToken(VerificationToken token) {
        Role role = roleRepository.findByName(RoleEnum.UNVERIFIED).orElseThrow();
        User user = token.getUser();
        user.deleteRole(role);
        userRepository.save(user);
        verificationTokenRepository.delete(token);
    }

    private LocalDateTime calculateExpiryDate() {
        return LocalDateTime.now().plusMinutes(expiration);
    }
    
}
