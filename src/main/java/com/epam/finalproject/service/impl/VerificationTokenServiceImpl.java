package com.epam.finalproject.service.impl;

import com.epam.finalproject.entity.Role;
import com.epam.finalproject.entity.RoleEnum;
import com.epam.finalproject.entity.User;
import com.epam.finalproject.entity.VerificationToken;
import com.epam.finalproject.repository.RoleRepository;
import com.epam.finalproject.repository.UserRepository;
import com.epam.finalproject.repository.VerificationTokenRepository;
import com.epam.finalproject.service.VerificationTokenService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

@Service
@Slf4j
public class VerificationTokenServiceImpl implements VerificationTokenService {

    @Value("${token.verify.expiration}")
    private Integer EXPIRATION = 1440;

    VerificationTokenRepository verificationTokenRepository;

    UserRepository userRepository;

    RoleRepository roleRepository;

    public VerificationTokenServiceImpl(VerificationTokenRepository verificationTokenRepository, UserRepository userRepository, RoleRepository roleRepository) {
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
        return LocalDateTime.now().plusMinutes(EXPIRATION);
    }
    
}
