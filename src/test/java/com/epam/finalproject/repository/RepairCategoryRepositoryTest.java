package com.epam.finalproject.repository;

import com.epam.finalproject.model.entity.RepairCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest()
@ActiveProfiles("test")
class RepairCategoryRepositoryTest {

    @Autowired
    RepairCategoryRepository repairCategoryRepository;

    @Test
    void findAll() {
        List<RepairCategory> roles = repairCategoryRepository.findAll();
        assertFalse(roles.isEmpty());
    }

    @Test
    void findByName() {
        RepairCategory repairCategory = repairCategoryRepository.findByKeyName("pc").orElseThrow();
        assertEquals("pc", repairCategory.getKeyName());
    }

}