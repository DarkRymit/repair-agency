package com.epam.finalproject.repository.specification;

import com.epam.finalproject.model.entity.User;
import com.epam.finalproject.model.search.UserSearch;
import com.epam.finalproject.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest()
@ActiveProfiles("test")
class UserSpecificationsTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void hasUsernameLikeEmptyString() {
        assertThrows(IllegalArgumentException.class, () -> UserSpecifications.hasUsernameLike(""));
    }

    @Test
    void hasUsernameLikeNull() {
        assertThrows(NullPointerException.class, () -> UserSpecifications.hasUsernameLike(null));
    }

    @Test
    void hasUsernameLikeString() {
        List<User> users = userRepository.findAll(UserSpecifications.hasUsernameLike("Red"));
        assertFalse(users.isEmpty());
    }

    @Test
    void matchSearch() {
        UserSearch search = UserSearch.builder()
                .username("Red")
                .build();
        List<User> users = userRepository.findAll(UserSpecifications.matchSearch(search));
        assertFalse(users.isEmpty());
    }
}