package com.epam.finalproject.repository;

import com.epam.finalproject.model.entity.RepairWork;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest()
@ActiveProfiles("test")
class RepairWorkRepositoryTest {

    @Autowired
    RepairWorkRepository repairWorkRepository;


    @Test
    void findByName() {
        List<RepairWork> repairWorks = repairWorkRepository.findByKeyName("battery-replace");
        assertFalse(repairWorks.isEmpty());
    }

    @Test
    void findByNameAndCategory() {
        RepairWork repairWork = repairWorkRepository.findByKeyNameAndCategory_KeyName("battery-replace", "notebook")
                .orElseThrow();
        assertEquals("battery-replace", repairWork.getKeyName());
        assertEquals("notebook", repairWork.getCategory().getKeyName());
    }

    @Test
    void findAll() {
        List<RepairWork> repairWorks = repairWorkRepository.findAll();
        assertFalse(repairWorks.isEmpty());
    }
}