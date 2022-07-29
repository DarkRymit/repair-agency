package com.epam.finalproject.repository;

import com.epam.finalproject.model.entity.Wallet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    @EntityGraph(attributePaths = {"user","moneyCurrency"})
    List<Wallet> findAllByUser_Username(String username);
    @EntityGraph(attributePaths = {"moneyCurrency"})
    Page<Wallet> findAllByUser_Username(Pageable pageable,String username);
    @EntityGraph(attributePaths = {"user","moneyCurrency"})
    Optional<Wallet> findDistinctByNameAndUser_Username(String name, String username);
}
