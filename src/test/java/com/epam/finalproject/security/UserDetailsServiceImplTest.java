package com.epam.finalproject.security;

import com.epam.finalproject.model.entity.Role;
import com.epam.finalproject.model.entity.enums.RoleEnum;
import com.epam.finalproject.model.entity.User;
import com.epam.finalproject.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class UserDetailsServiceImplTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserDetailsServiceImpl userDetailsService;

    User user;

    @BeforeEach
    void setMockOutput() {
        user = User.builder()
                .username("MockStriker")
                .email("mockstrike@gmail.com")
                .firstName("Mock")
                .password("secretPassword")
                .lastName("Striker")
                .phone("+380 63 108 7163")
                .roles(Set.of(new Role(RoleEnum.UNVERIFIED), new Role(RoleEnum.MASTER)))
                .build();
        when(userRepository.findByUsername("MockStriker")).thenReturn(Optional.of(user));
    }

    @Test
    void loadUserByUsername() {
        UserDetails userDetails = userDetailsService.loadUserByUsername("MockStriker");
        assertNotNull(userDetails);
        assertNotNull(userDetails.getUsername());
        assertEquals(user.getUsername(), userDetails.getUsername());
        assertNotNull(userDetails.getPassword());
        assertEquals(user.getPassword(), userDetails.getPassword());
        Collection<? extends GrantedAuthority> authority = userDetails.getAuthorities();
        assertFalse(authority.isEmpty());
        assertTrue(authority.contains(new SimpleGrantedAuthority("ROLE_" + RoleEnum.UNVERIFIED)));
        assertTrue(authority.contains(new SimpleGrantedAuthority("ROLE_" + RoleEnum.MASTER)));
    }
}