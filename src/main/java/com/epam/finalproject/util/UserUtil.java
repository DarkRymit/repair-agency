package com.epam.finalproject.util;

import com.epam.finalproject.entity.User;

import java.util.HashSet;

public class UserUtil {
    public static User createWithInitializedContainers() {
        return User.builder().roles(new HashSet<>()).wallets(new HashSet<>()).build();
    }
}
