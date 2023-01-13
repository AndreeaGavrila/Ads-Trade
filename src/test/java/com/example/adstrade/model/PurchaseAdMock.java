package com.example.adstrade.model;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PurchaseAdMock {

    public static @NotNull PurchaseAd purchaseAd() {
        PurchaseAd purchaseAd = new PurchaseAd();
        purchaseAd.setPurchase_id(1L);
        purchaseAd.setMessage("");
        purchaseAd.setDate("12-12-2022 12:12:12");
        purchaseAd.setCompleted(false);

        purchaseAd.setUser(purchaseAd().getUser());
        purchaseAd.setPurchase_topic(purchaseAd().getPurchase_topic());
        purchaseAd.setSaleAds(new ArrayList<>());
        return purchaseAd;
    }
}
