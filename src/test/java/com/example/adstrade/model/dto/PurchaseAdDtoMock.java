package com.example.adstrade.model.dto;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class PurchaseAdDtoMock {

    public static @NotNull PurchaseAdDto purchaseAdDto() {
        PurchaseAdDto dto = new PurchaseAdDto();
        dto.setMessage("message");
        dto.setDate("12-12-2022 12:12:12");
        dto.setCompleted(false);
        dto.setPurchase_topic("FOOD");

        dto.setUserId(1L);
        dto.setSaleAds(purchaseAdDto().getSaleAds());
        dto.setNotifications(new ArrayList<>());
        return dto;
    }

}
