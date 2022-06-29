package com.epam.finalproject.repository;

import com.epam.finalproject.model.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    List<Wallet> findAllByUser_Username(String username);
    Optional<Wallet> findDistinctByNameAndUser_Username(String name, String username);
}
