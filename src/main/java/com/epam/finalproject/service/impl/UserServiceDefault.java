package com.epam.finalproject.service.impl;

import com.epam.finalproject.payload.request.SignUpRequest;
import com.epam.finalproject.entity.Role;
import com.epam.finalproject.entity.RoleEnum;
import com.epam.finalproject.entity.User;
import com.epam.finalproject.exceptions.SingUpException;
import com.epam.finalproject.repository.RoleRepository;
import com.epam.finalproject.repository.UserRepository;
import com.epam.finalproject.service.UserService;
import com.epam.finalproject.util.UserUtil;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Service
@AllArgsConstructor
public class UserServiceDefault implements UserService {

    UserRepository userRepository;

    RoleRepository roleRepository;

    PasswordEncoder passwordEncoder;

    ModelMapper modelMapper;

    @Override
    @Transactional
    public User signUpNewUserAccount(SignUpRequest form) {
        try {
            return getSetUpAndSave(UserUtil::createWithInitializedContainers, user -> {
                modelMapper.map(form, user);
                user.chainer()
                        .password(passwordEncoder.encode(form.getPassword()))
                        .addRole(roleRepository.findByName(RoleEnum.UNVERIFIED).orElseThrow());
            });
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

    @Override
    public boolean isUserHaveRoleWithName(User user, RoleEnum roleName) {
        return user.getRoles().stream().map(Role::getName).anyMatch(eRole -> eRole.equals(roleName));
    }

    @Override
    public boolean isUserNotVerified(User user) {
        return isUserHaveRoleWithName(user,RoleEnum.UNVERIFIED);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    private User getSetUpAndSave(Supplier<User> userSupplier, Consumer<User> userConsumer) {
        User user = userSupplier.get();
        userConsumer.accept(user);
        userRepository.save(user);
        return user;
    }
}
