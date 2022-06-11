package com.epam.finalproject.service.impl;

import com.epam.finalproject.controller.SignUpForm;
import com.epam.finalproject.entity.Role;
import com.epam.finalproject.entity.RoleEnum;
import com.epam.finalproject.entity.User;
import com.epam.finalproject.exceptions.SingUpException;
import com.epam.finalproject.repository.RoleRepository;
import com.epam.finalproject.repository.UserRepository;
import com.epam.finalproject.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserServiceDefault implements UserService {

    UserRepository userRepository;

    RoleRepository roleRepository;

    PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User signUpNewUserAccount(SignUpForm form) {
        try {
            User user = User.builder()
                    .username(form.getUsername())
                    .email(form.getEmail())
                    .password(passwordEncoder.encode(form.getPassword()))
                    .firstName(form.getFirstName())
                    .lastName(form.getLastName())
                    .phone(form.getPhone())
                    .build();
            Role role = roleRepository.findByName(RoleEnum.ROLE_UNVERIFIED).orElseThrow();
            user.setRoles(Set.of(role));
            user.setWallets(new HashSet<>());
            userRepository.save(user);
            return user;
        } catch (Exception e) {
            throw new SingUpException(e);
        }
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
