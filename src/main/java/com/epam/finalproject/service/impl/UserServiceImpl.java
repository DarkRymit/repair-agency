package com.epam.finalproject.service.impl;

import com.epam.finalproject.aop.logging.Loggable;
import com.epam.finalproject.dto.UserDTO;
import com.epam.finalproject.exceptions.SingUpException;
import com.epam.finalproject.model.entity.Role;
import com.epam.finalproject.model.entity.User;
import com.epam.finalproject.model.entity.enums.RoleEnum;
import com.epam.finalproject.model.event.OnRegistrationCompleteEvent;
import com.epam.finalproject.payload.request.SignUpRequest;
import com.epam.finalproject.repository.RoleRepository;
import com.epam.finalproject.repository.UserRepository;
import com.epam.finalproject.service.UserService;
import com.epam.finalproject.util.UserUtil;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    RoleRepository roleRepository;

    PasswordEncoder passwordEncoder;

    ApplicationEventPublisher eventPublisher;
    ModelMapper modelMapper;

    @Override
    @Transactional
    @Loggable
    public User signUpNewUserAccount(SignUpRequest form) {
        User result;
        Role unverified = roleRepository.findByName(RoleEnum.UNVERIFIED).orElseThrow();
        Role customer = roleRepository.findByName(RoleEnum.CUSTOMER).orElseThrow();
        try {
            User user = UserUtil.createWithInitializedContainers();
            modelMapper.map(form, user);
            user.setPassword(passwordEncoder.encode(form.getPassword()));
            user.addRole(unverified);
            user.addRole(customer);
            user.setCreationDate(Instant.now());
            user.setLastModifiedDate(Instant.now());
            userRepository.save(user);
            result = user;
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, LocaleContextHolder.getLocale()));
        } catch (Exception e) {
            throw new SingUpException(e);
        }
        return result;
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
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserDTO findById(Long id) {
        return constructDTO(userRepository.findById(id).orElseThrow());
    }

    public UserDTO constructDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
}
