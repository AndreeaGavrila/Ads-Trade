package com.example.adstrade.model.dto;

import com.example.adstrade.enums.SaleStatus;
import org.jetbrains.annotations.NotNull;

public class SaleAdDtoMock {

    public static @NotNull SaleAdDto saleAdDto() {
        SaleAdDto dto = new SaleAdDto();
        dto.setMessage("message");
        dto.setDate("22-12-2022 15:05:01");
        dto.setLikes(1L);
        dto.setDislikes(0L);
        dto.setStatus(SaleStatus.OPEN.toString());

        dto.setPurchaseAdId(1L);
        dto.setUserId(1L);
        return dto;
    }
}