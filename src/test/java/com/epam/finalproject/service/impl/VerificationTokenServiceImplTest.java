package com.epam.finalproject.service.impl;

import com.epam.finalproject.entity.Role;
import com.epam.finalproject.entity.RoleEnum;
import com.epam.finalproject.entity.User;
import com.epam.finalproject.entity.VerificationToken;
import com.epam.finalproject.repository.RoleRepository;
import com.epam.finalproject.repository.UserRepository;
import com.epam.finalproject.repository.VerificationTokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class VerificationTokenServiceImplTest {

    static VerificationToken verificationToken;

    static User user;

    static Role role;

    @Mock
    UserRepository userRepository;

    @Mock
    RoleRepository roleRepository;

    @Mock
    VerificationTokenRepository verificationTokenRepository;

    @InjectMocks
    VerificationTokenServiceImpl verificationTokenService;

    @BeforeEach
    void setMockOutput() {

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
                .expiryDate(LocalDateTime.now().plusMinutes(10))
                .build();
    }

    @Test
    void createTokenForUser() {
        VerificationToken verificationToken = verificationTokenService.createTokenForUser(user);
        assertNotNull(verificationToken.getExpiryDate());
        assertNotNull(verificationToken.getToken());
        assertEquals(verificationToken.getUser(),user);
        assertTrue(verificationToken.getExpiryDate().isAfter(LocalDateTime.now()));

    }

    @Test
    void findByToken() {
        when(verificationTokenRepository.findByToken("token")).thenReturn(Optional.ofNullable(verificationToken));
        VerificationToken foundToken = verificationTokenService.findByToken(verificationToken.getToken()).orElseThrow();
        assertEquals(foundToken, verificationToken);
        assertEquals(foundToken.getToken(),"token");
    }

    @Test
    void isExpired() {
        assertFalse(verificationTokenService.isExpired(verificationToken));
        assertFalse(verificationTokenService.isExpired(verificationToken, () -> verificationToken.getExpiryDate()));
        assertTrue(verificationTokenService.isExpired(verificationToken, () -> verificationToken.getExpiryDate()
                .plusMinutes(1)));
    }

    @Test
    void verifyByToken() {
        when(roleRepository.findByName(RoleEnum.UNVERIFIED)).thenReturn(Optional.ofNullable(role));
        verificationTokenService.verifyByToken(verificationToken);
        assertFalse(user.getRoles().contains(role));
    }
}