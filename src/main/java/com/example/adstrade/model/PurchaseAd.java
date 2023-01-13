package com.example.adstrade.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Comparator;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PurchaseAd implements Comparator<PurchaseAd> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long purchase_id;

    private String message;
    private String date;

    private boolean completed;

    @OneToMany(mappedBy = "purchaseAd", cascade = CascadeType.ALL)
    private List<Notification> notifications;

    @OneToMany(mappedBy = "purchaseAd", cascade = CascadeType.ALL)
    private List<SaleAd> saleAds;

    @ManyToOne
    private User user;

    @ManyToOne
    private Purchase_Topic purchase_topic;

    @PreRemove
    public void preRemove() {
        this.saleAds.forEach(sale -> sale.setPurchaseAd(null));
        this.notifications.forEach(notification -> notification.setPurchaseAd(null));
    }

    private Integer noOfSaleAds(){
        return this.getSaleAds().size();
    }

    @Override
    public int compare(PurchaseAd o1, PurchaseAd o2) {
        return o2.noOfSaleAds() - o1.noOfSaleAds();
    }

}
