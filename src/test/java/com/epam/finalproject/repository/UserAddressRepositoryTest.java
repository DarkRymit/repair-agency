package com.epam.finalproject.repository;

import com.epam.finalproject.model.entity.UserAddress;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest()
@ActiveProfiles("test")
class UserAddressRepositoryTest {

    @Autowired
    UserAddressRepository userAddressRepository;

    @Test
    void findAll() {
        List<UserAddress> userAddresses = userAddressRepository.findAll();
        assertFalse(userAddresses.isEmpty());
    }

    @Test
    void findByUser_Username() {
        UserAddress userAddress = userAddressRepository.findByUser_Username("RedStriker").orElseThrow();
        assertEquals("Kyiv", userAddress.getCity());
        assertEquals("Ukraine", userAddress.getCountry());
    }
}