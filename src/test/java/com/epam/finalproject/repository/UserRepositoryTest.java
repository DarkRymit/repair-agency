package com.epam.finalproject.repository;

import com.epam.finalproject.entity.Role;
import com.epam.finalproject.entity.RoleEnum;
import com.epam.finalproject.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest()
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Test
    void findAll() {
        List<User> users = userRepository.findAll();
        assertFalse(users.isEmpty());
    }

    @Test
    @Transactional
    void findByUsernameAndDelete() {
        User user = userRepository.findByUsername("RedStriker").orElseThrow();
        userRepository.delete(user);
        assertTrue(userRepository.findByUsername("RedStriker").isEmpty());
    }

    @Test
    @Transactional
    void addUserWithRoleAndFindByUsername() {
        User user = User.builder()
                .username("NoneStriker")
                .email("nonestrike@gmail.com")
                .password("pass3")
                .firstName("None")
                .lastName("Strike")
                .phone("+380 63 108 7167")
                .build();
        Role role = roleRepository.findByName(RoleEnum.UNVERIFIED).orElseThrow();
        user.setRoles(Set.of(role));
        user.setWallets(new HashSet<>());
        userRepository.save(user);
        User createdUser = userRepository.findByUsername("NoneStriker").orElseThrow();
        assertNotNull(user.getWallets());
        assertNotNull(user.getRoles());
        assertTrue(user.getRoles().contains(role));
        assertEquals("None", createdUser.getFirstName());
        assertEquals("Strike", createdUser.getLastName());
    }

    @Test
    void findByUsername() {
        User user = userRepository.findByUsername("RedStriker").orElseThrow();
        assertEquals("Red", user.getFirstName());
        assertEquals("Strike", user.getLastName());
    }

    @Test
    void findByEmail() {
        User user = userRepository.findByEmail("redstrike@gmail.com").orElseThrow();
        assertEquals("Red", user.getFirstName());
        assertEquals("Strike", user.getLastName());
    }

    @Test
    void existsByUsername() {
        assertTrue(userRepository.existsByUsername("RedStriker"));
        assertFalse(userRepository.existsByUsername("NotExist"));
    }

    @Test
    void existsByEmail() {
        assertTrue(userRepository.existsByEmail("redstrike@gmail.com"));
        assertFalse(userRepository.existsByEmail("notexist@gmail.com"));
    }

    @Test
    void findALLByRoles_Name() {
        List<User> users = userRepository.findALLByRoles_Name(RoleEnum.UNVERIFIED);
        assertFalse(users.isEmpty());
    }
}