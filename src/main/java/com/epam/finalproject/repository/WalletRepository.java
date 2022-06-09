package com.epam.finalproject.repository;

import com.epam.finalproject.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import java.util.List;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    List<Wallet> findAllByUser_Username(String username);
    Optional<Wallet> findDistinctByNameAndUser_Username(String name, String username);
}
