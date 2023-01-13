package com.example.adstrade.model;

import com.example.adstrade.enums.NotificationStatus;
import com.example.adstrade.enums.NotificationType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private NotificationType notification_type;

    @Enumerated(EnumType.STRING)
    private NotificationStatus notification_status;

    @ManyToOne
    private SaleAd saleAd;

    @ManyToOne
    private PurchaseAd purchaseAd;

    @PreRemove
    public void preRemove(){
        this.setSaleAd(null);
    }

}