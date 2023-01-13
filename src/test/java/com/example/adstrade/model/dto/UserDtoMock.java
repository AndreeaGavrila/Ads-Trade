package com.example.adstrade.model.dto;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class UserDtoMock {

    public static @NotNull UserDto userDto() {
        UserDto dto = new UserDto();
        dto.setEmail("email@email.com");
        dto.setFirstName("firstName");
        dto.setLastName("lastName");
        dto.setDescription("description");
        dto.setVerified_ads(5);

        dto.setPurchaseAds(new ArrayList<>());
        dto.setSaleAds(new ArrayList<>());
        dto.setUsersBadges(new ArrayList<>());
        return dto;
    }
}
