package com.epam.finalproject.repository;

import com.epam.finalproject.model.entity.enums.RepairCategoryName;
import com.epam.finalproject.model.entity.RepairWork;
import com.epam.finalproject.model.entity.enums.RepairWorkName;
import com.epam.finalproject.model.entity.enums.RepairWorkStatus;
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
        List<RepairWork> repairWorks = repairWorkRepository.findByName(RepairWorkName.BATTERY_REPLACE);
        assertFalse(repairWorks.isEmpty());
    }

    @Test
    void findByNameAndCategory() {
        RepairWork repairWork = repairWorkRepository.findByNameAndCategory_Name(RepairWorkName.BATTERY_REPLACE, RepairCategoryName.NOTEBOOK)
                .orElseThrow();
        assertEquals(RepairWorkName.BATTERY_REPLACE, repairWork.getName());
        assertEquals(RepairCategoryName.NOTEBOOK, repairWork.getCategory().getName());
        assertTrue(repairWork.getStatuses().contains(RepairWorkStatus.VIP));
    }

    @Test
    void findAll() {
        List<RepairWork> repairWorks = repairWorkRepository.findAll();
        assertFalse(repairWorks.isEmpty());
    }
}