package com.example.adstrade.model;

import com.example.adstrade.enums.NotificationStatus;
import com.example.adstrade.enums.NotificationType;
import org.jetbrains.annotations.NotNull;

public class NotificationMock {

    public static @NotNull Notification notification(){
        Notification notification = new Notification();
        notification.setId(1L);
        notification.setNotification_type(NotificationType.RECEIVED_PURCHASE_AD);
        notification.setNotification_status(NotificationStatus.UNSEEN);

        SaleAd saleAd = new SaleAd();
        saleAd.setSale_id(1L);
        notification.setSaleAd(saleAd);

        PurchaseAd purchaseAd = new PurchaseAd();
        purchaseAd.getPurchase_id();
        notification.setPurchaseAd(purchaseAd);

        return notification;
    }
}
