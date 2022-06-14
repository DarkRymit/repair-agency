package com.epam.finalproject.util;

import com.epam.finalproject.entity.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserUtilTest {

    @Test
    void createWithInitializedContainers() {
        User user = UserUtil.createWithInitializedContainers();
        assertNotNull(user.getRoles());
        assertNotNull(user.getWallets());
    }
}