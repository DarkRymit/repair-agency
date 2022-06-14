package com.epam.finalproject.security;

import com.epam.finalproject.entity.Role;
import com.epam.finalproject.entity.RoleEnum;
import com.epam.finalproject.entity.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


class UserDetailsImplTest {

    static User user;

    @BeforeAll
    public static void setup() {
        user = User.builder()
                .username("NotDBStriker")
                .email("notdbstrike@gmail.com")
                .firstName("NotDB")
                .password("secretPassword")
                .lastName("Striker")
                .phone("+380 63 108 7168")
                .roles(Set.of(new Role(RoleEnum.UNVERIFIED), new Role(RoleEnum.MANAGER)))
                .build();
    }

    @Test
    void of() {
        UserDetails userDetails = UserDetailsImpl.of(user);
        assertEquals(user.getUsername(), userDetails.getUsername());
        assertEquals(user.getPassword(), userDetails.getPassword());
        Collection<? extends GrantedAuthority> authority = userDetails.getAuthorities();
        assertFalse(authority.isEmpty());

    }

    @Test
    void getAuthorities() {
        UserDetails userDetails = UserDetailsImpl.of(user);
        Collection<? extends GrantedAuthority> authority = userDetails.getAuthorities();
        assertFalse(authority.isEmpty());
        assertTrue(authority.contains(new SimpleGrantedAuthority("ROLE_" + RoleEnum.UNVERIFIED)));
        assertTrue(authority.contains(new SimpleGrantedAuthority("ROLE_" + RoleEnum.MANAGER)));
    }

    @Test
    void isAccountNonExpired() {
        UserDetails userDetails = UserDetailsImpl.of(user);
        assertTrue(userDetails.isAccountNonExpired());
    }

    @Test
    void isAccountNonLocked() {
        UserDetails userDetails = UserDetailsImpl.of(user);
        assertTrue(userDetails.isAccountNonLocked());
    }

    @Test
    void isCredentialsNonExpired() {
        UserDetails userDetails = UserDetailsImpl.of(user);
        assertTrue(userDetails.isCredentialsNonExpired());
    }

    @Test
    void isEnabled() {
        UserDetails userDetails = UserDetailsImpl.of(user);
        assertTrue(userDetails.isEnabled());
    }

    @Test
    void getUsername() {
        UserDetails userDetails = UserDetailsImpl.of(user);
        assertEquals(user.getUsername(), userDetails.getUsername());
    }

    @Test
    void getPassword() {
        UserDetails userDetails = UserDetailsImpl.of(user);
        assertEquals(user.getPassword(), userDetails.getPassword());
    }
}