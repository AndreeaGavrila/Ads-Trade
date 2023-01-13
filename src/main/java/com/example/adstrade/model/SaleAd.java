package com.example.adstrade.model;

import com.example.adstrade.enums.SaleStatus;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.Comparator;
import java.util.List;

@Entity
@Getter
@Setter
public class SaleAd implements Comparator<SaleAd>, Comparable<SaleAd> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sale_id;

    private String message;
    private String date;

    private long likes;
    private long dislikes;

    @Enumerated(EnumType.STRING)
    private SaleStatus status;

    @ManyToOne
    //@JoinColumn(name="purchase_ad_id")
    private PurchaseAd purchaseAd;

    @ManyToOne
    private User user;

    @ManyToOne
    private Sale_Topic sale_topic;

    @OneToMany(mappedBy = "saleAd", cascade = CascadeType.ALL)
    private List<Notification> notifications;

    @PreRemove
    public void preRemove(){
        this.setPurchaseAd(null);
        this.notifications.forEach(notification -> notification.setSaleAd(null));
    }

    @Override
    public int compareTo(@NotNull SaleAd o) {
        return(this.date).compareTo(o.date);
    }

    @Override
    public int compare(SaleAd o1, SaleAd o2) {
        return (int) (o1.likes - o2.likes);
    }

}
