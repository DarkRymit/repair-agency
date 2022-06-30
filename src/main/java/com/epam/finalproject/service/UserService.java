package com.epam.finalproject.service;

import com.epam.finalproject.model.entity.enums.RoleEnum;
import com.epam.finalproject.model.entity.User;
import com.epam.finalproject.payload.request.SignUpRequest;

import java.util.Optional;

public interface UserService {
    User signUpNewUserAccount(SignUpRequest form);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean isUserHaveRoleWithName(User user, RoleEnum roleName);

    boolean isUserNotVerified(User user);

    Optional<User> findByEmail(String email);
}
