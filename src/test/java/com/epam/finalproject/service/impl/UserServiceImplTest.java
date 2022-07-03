package com.epam.finalproject.service.impl;

import com.epam.finalproject.model.entity.Role;
import com.epam.finalproject.model.entity.User;
import com.epam.finalproject.model.entity.enums.RoleEnum;
import com.epam.finalproject.payload.request.SignUpRequest;
import com.epam.finalproject.repository.RoleRepository;
import com.epam.finalproject.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @Mock
    RoleRepository roleRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    UserServiceImpl userService;

    User user;

    SignUpRequest request;

    @BeforeEach
    void setMockOutput() {
        user = User.builder()
                .id(404L)
                .username("NotDBStriker")
                .email("notdbstrike@gmail.com")
                .firstName("NotDB")
                .password("secretPassword")
                .lastName("Striker")
                .phone("+380 63 108 7168")
                .roles(new HashSet<>(Set.of(new Role(RoleEnum.CUSTOMER), new Role(RoleEnum.UNVERIFIED))))
                .build();
        request = new SignUpRequest();
        new ModelMapper().map(user, request);
        userService = new UserServiceImpl(userRepository,roleRepository,passwordEncoder,new ModelMapper());
    }

    @Test
    void signUpNewUserAccount_ShouldReturnUnverifiedUser_WhenGiveSingUpRequest() {
        Role role = new Role(RoleEnum.UNVERIFIED);
        when(roleRepository.findByName(RoleEnum.UNVERIFIED)).thenReturn(Optional.of(role));
        when(passwordEncoder.encode(any())).thenReturn("secretPassword");

        User result = userService.signUpNewUserAccount(request);

        assertEquals(user.getUsername(), result.getUsername());
        assertTrue(result.getRoles().contains(role));
    }

    @Test
    void existsByUsername_ShouldReturnTrue_WhenUserExistsBySpecifiedUsername() {
        when(userRepository.existsByUsername("NotDBStriker")).thenReturn(true);
        assertTrue(userService.existsByUsername("NotDBStriker"));
    }

    @Test
    void existsByEmail_ShouldReturnTrue_WhenUserExistsBySpecifiedEmail() {
        when(userRepository.existsByEmail("notdbstrike@gmail.com")).thenReturn(true);
        assertTrue(userService.existsByEmail("notdbstrike@gmail.com"));
    }

    @Test
    void isUserHaveRoleWithName_ShouldReturnTrue_WhenUserHaveRoleUnverified() {
        assertTrue(userService.isUserHaveRoleWithName(user, RoleEnum.UNVERIFIED));
    }

    @Test
    void isUserNotVerified_ShouldReturnTrue_WhenUserHaveRoleUnverified() {
        assertTrue(userService.isUserNotVerified(user));
    }

    @Test
    void isUserNotVerified_ShouldReturnFalse_WhenUserNotHaveRoleUnverified() {
        user.getRoles().remove(new Role(RoleEnum.UNVERIFIED));
        assertFalse(userService.isUserNotVerified(user));
    }

    @Test
    void findByEmail_ShouldReturnCorrectUser_WhenUserFindBySpecifiedEmail() {
        when(userRepository.findByEmail("notdbstrike@gmail.com")).thenReturn(Optional.of(user));
        User foundUser = userService.findByEmail("notdbstrike@gmail.com").orElseThrow();
        assertEquals(user.getUsername(), foundUser.getUsername());
        assertEquals(user.getEmail(), foundUser.getEmail());
        assertEquals("notdbstrike@gmail.com", foundUser.getEmail());
        assertEquals("NotDB", foundUser.getFirstName());
    }
}