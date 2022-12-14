package com.epam.finalproject.repository;

import com.epam.finalproject.model.entity.Wallet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest()
@ActiveProfiles("test")
class WalletRepositoryTest {

    @Autowired
    WalletRepository walletRepository;

    @Test
    void findAll() {
        List<Wallet> wallets = walletRepository.findAll();
        assertFalse(wallets.isEmpty());
    }

    @Test
    void findAllByUser_Username() {
        List<Wallet> wallets = walletRepository.findAllByUser_Username("RedStriker");
        assertFalse(wallets.isEmpty());
    }

    @Test
    void findDistinctByNameAndUser_Username() {
        Wallet wallet = walletRepository.findDistinctByNameAndUser_Username("Default","RedStriker").orElseThrow();
        assertEquals("Default", wallet.getName());
        assertEquals("RedStriker", wallet.getUser().getUsername());
        assertEquals("USD", wallet.getMoneyCurrency().getCode());
        assertEquals(0,BigDecimal.valueOf(100).compareTo(wallet.getMoneyAmount()));
    }
}