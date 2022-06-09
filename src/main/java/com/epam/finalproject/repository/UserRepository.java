package com.epam.finalproject.repository;

import com.epam.finalproject.entity.Role;
import com.epam.finalproject.entity.RoleEnum;
import com.epam.finalproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
//    @Query("SELECT u FROM User u where u.username = :username")
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String login);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

    List<User> findALLByRoles_Name(RoleEnum role);
}
