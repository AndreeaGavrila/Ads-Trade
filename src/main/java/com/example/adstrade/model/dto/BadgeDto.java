package com.example.adstrade.model.dto;

import com.example.adstrade.model.Badge;
import com.example.adstrade.model.SaleAd;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.stream.Collectors;

@Data
public class BadgeDto {

    @Enumerated(EnumType.STRING)
    private String name;
    private int points;

    public static @NotNull BadgeDto convertEntityToDto(@NotNull Badge badge){
        BadgeDto dto = new BadgeDto();
        dto.setName(badge.getName().toString());
        dto.setPoints((badge.getPoints()));
        return dto;
    }

}
