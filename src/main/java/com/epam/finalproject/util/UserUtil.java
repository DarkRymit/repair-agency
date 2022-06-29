package com.epam.finalproject.util;

import com.epam.finalproject.model.entity.User;

import java.util.HashSet;

public interface UserUtil {
    static User createWithInitializedContainers() {
        return User.builder().roles(new HashSet<>()).wallets(new HashSet<>()).build();
    }
}
