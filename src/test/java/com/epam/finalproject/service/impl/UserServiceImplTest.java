package com.epam.finalproject.service.impl;

import com.epam.finalproject.dto.UserDTO;
import com.epam.finalproject.exceptions.SingUpException;
import com.epam.finalproject.model.entity.Role;
import com.epam.finalproject.model.entity.User;
import com.epam.finalproject.model.entity.enums.RoleEnum;
import com.epam.finalproject.payload.request.SignUpRequest;
import com.epam.finalproject.repository.RoleRepository;
import com.epam.finalproject.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class UserServiceImplTest {

    @Mock
    static UserRepository userRepository;

    @Mock
    static RoleRepository roleRepository;

    @Mock
    static PasswordEncoder passwordEncoder;


    @Mock
    static ApplicationEventPublisher publisher;


    static ModelMapper modelMapper;

    static UserServiceImpl userService;

    static User user;

    static Role unverified;

    static Role customer;

    @BeforeAll
    static void getResources() {
        modelMapper = new ModelMapper();
        modelMapper.typeMap(SignUpRequest.class, User.class).addMappings(mapper -> mapper.skip(User::setPassword));
        modelMapper.typeMap(User.class, UserDTO.class)
                .addMappings(mapper -> mapper.using(instantZonedDateTimeConverter())
                        .map(User::getCreationDate, UserDTO::setCreationDate))
                .addMappings(mapper -> mapper.using(instantZonedDateTimeConverter()));
        user = User.builder()
                .id(404L)
                .username("NotDBStriker")
                .email("notdbstrike@gmail.com")
                .firstName("NotDB")
                .password("secretPassword")
                .lastName("Striker")
                .phone("+380 63 108 7168")
                .creationDate(Instant.now())
                .roles(new HashSet<>(Set.of(new Role[]{new Role(RoleEnum.CUSTOMER)})))
                .build();
        unverified = new Role(1L, RoleEnum.UNVERIFIED);
        customer = new Role(2L, RoleEnum.CUSTOMER);
    }
    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder,
                publisher,modelMapper);
    }

    private static Converter<Instant, ZonedDateTime> instantZonedDateTimeConverter() {
        return mappingContext -> mappingContext.getSource().atZone(TimeZone.getDefault().toZoneId());
    }

    @Test
    void signUpNewUserAccountShouldReturnCorrectUser() {
        SignUpRequest request = new SignUpRequest("test", "notdbstrike@gmail.com", "password", "First", "Last",
                "+380 63 108 7198");
        when(userRepository.save(any())).then(returnsFirstArg());

        when(passwordEncoder.encode(any())).thenReturn("Encoded");

        when(roleRepository.findByName(RoleEnum.UNVERIFIED)).thenReturn(Optional.of(unverified));

        when(roleRepository.findByName(RoleEnum.CUSTOMER)).thenReturn(Optional.of(customer));

        User returned = userService.signUpNewUserAccount(request);

        assertEquals("Encoded", returned.getPassword());

        assertTrue(returned.getRoles().contains(unverified));
        assertTrue(returned.getRoles().contains(customer));

    }

    @Test
    void signUpNewUserAccountShouldThrowSingUpExceptionWhenRepositoryException() {
        SignUpRequest request = new SignUpRequest("test", "notdbstrike@gmail.com", "password", "First", "Last",
                "+380 63 108 7198");
        when(userRepository.save(any())).thenThrow(new DataAccessException("some exception"){});

        when(passwordEncoder.encode("secretPassword")).thenReturn("Encoded");


        when(roleRepository.findByName(RoleEnum.UNVERIFIED)).thenReturn(Optional.of(unverified));

        when(roleRepository.findByName(RoleEnum.CUSTOMER)).thenReturn(Optional.of(customer));

        assertThrows(SingUpException.class, () -> userService.signUpNewUserAccount(request));

    }

    @Test
    void existsByUsernameShouldReturnTrueWhenExistUser() {
        when(userRepository.existsByUsername("NotDBStriker")).thenReturn(true);
        assertTrue(userService.existsByUsername("NotDBStriker"));
    }

    @Test
    void existsByUsernameShouldReturnFalseWhenNotExistUser() {
        when(userRepository.existsByUsername("NotddDBStriker")).thenReturn(false);
        assertFalse(userService.existsByUsername("NotddDBStriker"));
    }

    @Test
    void existsByEmailShouldReturnTrueWhenExistUser() {
        when(userRepository.existsByUsername("NotDBStriker")).thenReturn(true);
        assertTrue(userService.existsByUsername("NotDBStriker"));
    }

    @Test
    void existsByEmailShouldReturnFalseWhenNotExistUser() {
        when(userRepository.existsByUsername("NotddDBStriker")).thenReturn(false);
        assertFalse(userService.existsByUsername("NotddDBStriker"));
    }

    @Test
    void findByEmailShouldReturnCorrectUserWhenByEmail() {
        when(userRepository.findByEmail("notdbstrike@gmail.com")).thenReturn(Optional.of(user));
        User foundUser = userService.findByEmail("notdbstrike@gmail.com").orElseThrow();
        assertEquals("notdbstrike@gmail.com", foundUser.getEmail());
    }

    @Test
    void findByIdShouldReturnCorrectUserWhenByExistId() {
        when(userRepository.findById(404L)).thenReturn(Optional.of(user));
        UserDTO foundUser = userService.findById(404L);
        assertEquals("notdbstrike@gmail.com", foundUser.getEmail());
        assertNotNull(foundUser.getCreationDate());
    }
}