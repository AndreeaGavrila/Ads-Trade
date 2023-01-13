package com.example.adstrade.model.dto;

import com.example.adstrade.enums.TopicValue;
import org.jetbrains.annotations.NotNull;

public class BadgeDtoMock {

    public static @NotNull BadgeDto badgeDto() {
        BadgeDto dto = new BadgeDto();
        dto.setName("");
        dto.setPoints(0);
        return dto;
    }
}
