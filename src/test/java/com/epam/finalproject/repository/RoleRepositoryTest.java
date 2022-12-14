package com.epam.finalproject.repository;

import com.epam.finalproject.model.entity.Role;
import com.epam.finalproject.model.entity.enums.RoleEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest()
@ActiveProfiles("test")
class RoleRepositoryTest {

    @Autowired
    RoleRepository roleRepository;

    @Test
    void findAll() {
        List<Role> roles = roleRepository.findAll();
        assertFalse(roles.isEmpty());
    }

    @Test
    void findByName() {
        Role role = roleRepository.findByName(RoleEnum.UNVERIFIED).orElseThrow();
        assertEquals(RoleEnum.UNVERIFIED, role.getName());
    }

}