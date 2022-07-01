package com.epam.finalproject.service.impl;

import com.epam.finalproject.model.entity.PasswordResetToken;
import com.epam.finalproject.model.entity.Role;
import com.epam.finalproject.model.entity.User;
import com.epam.finalproject.model.entity.VerificationToken;
import com.epam.finalproject.model.entity.enums.RoleEnum;
import com.epam.finalproject.payload.request.NewPasswordRequest;
import com.epam.finalproject.repository.PasswordResetTokenRepository;
import com.epam.finalproject.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
class PasswordResetTokenServiceImplTest {

    PasswordResetToken passwordResetToken;

    User user;

    @Mock
    UserRepository userRepository;

    PasswordEncoder passwordEncoder;

    @Mock
    PasswordResetTokenRepository passwordResetTokenRepository;

    @Value("${token.verify.expiration}")
    Integer expiration;

    PasswordResetTokenServiceImpl passwordResetTokenService;

    @BeforeEach
    void setMockOutput() {

        passwordEncoder = new PasswordEncoder() {

            @Override
            public String encode(CharSequence rawPassword) {
                return String.valueOf(rawPassword).intern();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return rawPassword.equals(encodedPassword);
            }
        };

        passwordResetTokenService = new PasswordResetTokenServiceImpl(expiration,passwordResetTokenRepository,userRepository,passwordEncoder);
        user = User.builder()
                .id(404L)
                .username("NotDBStriker")
                .email("notdbstrike@gmail.com")
                .firstName("NotDB")
                .password("secretPassword")
                .lastName("Striker")
                .phone("+380 63 108 7168")
                .roles(new HashSet<>(Set.of(new Role(RoleEnum.CUSTOMER))))
                .build();
        passwordResetToken = PasswordResetToken.builder()
                .id(2L)
                .token("token")
                .user(user)
                .expiryDate(LocalDateTime.now().plusMinutes(10))
                .build();
    }


    @Test
    void createTokenForUser() {
        PasswordResetToken passwordResetToken = passwordResetTokenService.createTokenForUser(user);
        assertNotNull(passwordResetToken.getExpiryDate());
        assertNotNull(passwordResetToken.getToken());
        assertEquals(passwordResetToken.getUser(),user);
        assertTrue(passwordResetToken.getExpiryDate().isAfter(LocalDateTime.now()));

    }

    @Test
    void findByToken() {
        when(passwordResetTokenRepository.findByToken("token")).thenReturn(Optional.ofNullable(passwordResetToken));
        PasswordResetToken foundToken = passwordResetTokenService.findByToken(passwordResetToken.getToken()).orElseThrow();
        assertEquals(foundToken, passwordResetToken);
        assertEquals("token", foundToken.getToken());

    }

    @Test
    void isExpired() {
        assertFalse(passwordResetTokenService.isExpired(passwordResetToken));
        assertFalse(passwordResetTokenService.isExpired(passwordResetToken, () -> passwordResetToken.getExpiryDate()));
        assertTrue(passwordResetTokenService.isExpired(passwordResetToken, () -> passwordResetToken.getExpiryDate()
                .plusMinutes(1)));
    }

    @Test
    void newPassword() {
        passwordResetTokenService.newPassword(passwordResetToken,new NewPasswordRequest("password"));
        assertEquals("password",user.getPassword());
    }
}