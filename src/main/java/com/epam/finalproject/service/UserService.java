package com.epam.finalproject.service;

import com.epam.finalproject.controller.SignUpForm;
import com.epam.finalproject.entity.RoleEnum;
import com.epam.finalproject.entity.User;

public interface UserService {
    User signUpNewUserAccount(SignUpForm form);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean isUserHaveRoleWithName(User user, RoleEnum roleName);

    boolean isUserNotVerified(User user);
}
