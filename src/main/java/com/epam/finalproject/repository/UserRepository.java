package com.epam.finalproject.repository;

import com.epam.finalproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
//    @Query("SELECT u FROM User u where u.username = :login")
//    Optional<User> findByUsername(@Param("login") String login);
    Optional<User> findByUsername(String login);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
