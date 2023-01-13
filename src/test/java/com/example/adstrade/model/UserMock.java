package com.example.adstrade.model;

import com.example.adstrade.enums.Role;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;

public class UserMock {

    public static @NotNull User user() {
        User user = new User();
        user.setId(1L);
        user.setPassword("pass");
        user.setEmail("email@email.com");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setRole(Role.ROLE_USER);
        user.setVerified_ads(0);
        user.setVotedSaleAds(new HashSet<>());

        user.setBadges(new HashSet<>());
        user.setPurchaseAds(new ArrayList<>());
        user.setSaleAds(new ArrayList<>());
        user.setBadges(new HashSet<>());

        return user;
    }
}
