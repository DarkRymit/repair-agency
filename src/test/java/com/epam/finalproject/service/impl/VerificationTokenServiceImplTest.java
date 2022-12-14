package com.epam.finalproject.service.impl;

import com.epam.finalproject.model.entity.Role;
import com.epam.finalproject.model.entity.enums.RoleEnum;
import com.epam.finalproject.model.entity.User;
import com.epam.finalproject.model.entity.VerificationToken;
import com.epam.finalproject.repository.RoleRepository;
import com.epam.finalproject.repository.UserRepository;
import com.epam.finalproject.repository.VerificationTokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith({SpringExtension.class})
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@TestPropertySource(properties = { "token.verify.expiration = 1440" })
class VerificationTokenServiceImplTest {

    VerificationToken verificationToken;

    User user;

    Role role;

    @Mock
    UserRepository userRepository;

    @Mock
    RoleRepository roleRepository;

    @Mock
    VerificationTokenRepository verificationTokenRepository;

    @Value("${token.verify.expiration}")
    Integer expiration;

    VerificationTokenServiceImpl verificationTokenService;

    @BeforeEach
    void setMockOutput() {
        verificationTokenService = new VerificationTokenServiceImpl(expiration,verificationTokenRepository,userRepository,roleRepository);
        role = new Role(RoleEnum.UNVERIFIED);
        user = User.builder()
                .id(404L)
                .username("NotDBStriker")
                .email("notdbstrike@gmail.com")
                .firstName("NotDB")
                .password("secretPassword")
                .lastName("Striker")
                .phone("+380 63 108 7168")
                .roles(new HashSet<>(Set.of(new Role[]{role})))
                .build();
        verificationToken = VerificationToken.builder()
                .id(2L)
                .token("token")
                .user(user)
                .expiryDate(Instant.now().plusSeconds(10*60))
                .build();
    }

    @Test
    void createTokenForUser() {
        VerificationToken verificationToken = verificationTokenService.createTokenForUser(user);
        assertNotNull(verificationToken.getExpiryDate());
        assertNotNull(verificationToken.getToken());
        assertEquals(verificationToken.getUser(),user);
        assertTrue(verificationToken.getExpiryDate().isAfter(Instant.now()));

    }

    @Test
    void findByToken() {
        when(verificationTokenRepository.findByToken("token")).thenReturn(Optional.ofNullable(verificationToken));
        VerificationToken foundToken = verificationTokenService.findByToken(verificationToken.getToken()).orElseThrow();
        assertEquals(foundToken, verificationToken);
        assertEquals("token", foundToken.getToken());
    }

    @Test
    void isExpired() {
        assertFalse(verificationTokenService.isExpired(verificationToken));
        assertFalse(verificationTokenService.isExpired(verificationToken, () -> verificationToken.getExpiryDate()));
        assertTrue(verificationTokenService.isExpired(verificationToken, () -> verificationToken.getExpiryDate()
                .plusSeconds(60)));
    }

    @Test
    void verifyByToken() {
        when(roleRepository.findByName(RoleEnum.UNVERIFIED)).thenReturn(Optional.ofNullable(role));
        verificationTokenService.verifyByToken(verificationToken);
        assertFalse(user.getRoles().contains(role));
    }
}