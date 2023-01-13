package com.example.adstrade.model;

import com.example.adstrade.enums.TopicValue;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Purchase_Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TopicValue name;

    @OneToMany(mappedBy = "purchase_topic", cascade= CascadeType.ALL)
    private List<PurchaseAd> purchaseAds;

    @PreRemove
    public void preRemove(){
        this.purchaseAds.forEach(purchase -> purchase.setPurchase_topic(null));
    }

}