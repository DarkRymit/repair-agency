package com.epam.finalproject.repository;

import com.epam.finalproject.RepairAgencyApplication;
import com.epam.finalproject.entity.RoleEnum;
import com.epam.finalproject.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest()
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

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
        List<User> users = userRepository.findALLByRoles_Name(RoleEnum.ROLE_UNVERIFIED);
        assertFalse(users.isEmpty());
    }
}