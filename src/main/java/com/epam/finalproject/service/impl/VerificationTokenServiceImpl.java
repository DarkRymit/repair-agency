package com.epam.finalproject.service.impl;

import com.epam.finalproject.entity.Role;
import com.epam.finalproject.entity.RoleEnum;
import com.epam.finalproject.entity.User;
import com.epam.finalproject.entity.VerificationToken;
import com.epam.finalproject.repository.RoleRepository;
import com.epam.finalproject.repository.UserRepository;
import com.epam.finalproject.repository.VerificationTokenRepository;
import com.epam.finalproject.service.VerificationTokenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class VerificationTokenServiceImpl implements VerificationTokenService {

    @Value("${token.verify.expiration}")
    private Integer EXPIRATION;

    public VerificationTokenServiceImpl(VerificationTokenRepository verificationTokenRepository, UserRepository userRepository, RoleRepository roleRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    VerificationTokenRepository verificationTokenRepository;

    UserRepository userRepository;

    RoleRepository roleRepository;

    @Override
    public String createTokenForUser(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = VerificationToken.builder()
                .token(token)
                .user(user)
                .expiryDate(calculateExpiryDate())
                .build();
        verificationTokenRepository.save(verificationToken);
        log.info("Create token :" + verificationToken);
        return token;
    }

    @Override
    public Optional<VerificationToken> findByToken(String token) {
        return verificationTokenRepository.findByToken(token);
    }

    @Override
    public boolean isExpired(VerificationToken verificationToken) {
        final Calendar cal = Calendar.getInstance();
        return (verificationToken.getExpiryDate()
                .getTime() - cal.getTime()
                .getTime()) <= 0;
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

    private Date calculateExpiryDate() {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, EXPIRATION);
        return new Date(cal.getTime().getTime());
    }
    
}
