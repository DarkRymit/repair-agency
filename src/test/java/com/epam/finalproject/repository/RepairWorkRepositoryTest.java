package com.epam.finalproject.repository;

import com.epam.finalproject.entity.RepairWork;
import com.epam.finalproject.entity.RepairWorkName;
import com.epam.finalproject.entity.RepairWorkStatus;
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
        RepairWork repairWork = repairWorkRepository.findByName(RepairWorkName.BATTERY_REPLACE).orElseThrow();
        assertEquals(RepairWorkName.BATTERY_REPLACE, repairWork.getName());
        assertTrue(repairWork.getStatuses().contains(RepairWorkStatus.VIP));
    }

    @Test
    void findAll() {
        List<RepairWork> repairWorks = repairWorkRepository.findAll();
        assertFalse(repairWorks.isEmpty());
    }
}