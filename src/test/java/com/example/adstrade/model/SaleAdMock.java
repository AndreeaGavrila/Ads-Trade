package com.example.adstrade.model;

import com.example.adstrade.enums.SaleStatus;
import org.jetbrains.annotations.NotNull;

public class SaleAdMock {

    public static @NotNull SaleAd saleAd() {
        SaleAd saleAd = new SaleAd();
        saleAd.setSale_id(1L);
        saleAd.setMessage("");
        saleAd.setDate("12-12-2022 12:12:12");
        saleAd.setLikes(100);
        saleAd.setDislikes(12);
        saleAd.setStatus(SaleStatus.OPEN);

        PurchaseAd purchaseAd = new PurchaseAd();
        purchaseAd.setPurchase_id(1L);
        purchaseAd.setMessage("");
        purchaseAd.setDate("12-12-2022 12:12:12");

        saleAd.setPurchaseAd(purchaseAd);
        saleAd.setUser(saleAd().getUser());
        return saleAd;
    }
}
