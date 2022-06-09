package com.epam.finalproject.repository;

import com.epam.finalproject.entity.RoleEnum;
import com.epam.finalproject.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleEnum name);

}
