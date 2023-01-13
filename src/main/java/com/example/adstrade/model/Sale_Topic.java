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
public class Sale_Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TopicValue name;

    @OneToMany(mappedBy = "sale_topic", cascade= CascadeType.ALL)
    private List<SaleAd> saleAds;

    @PreRemove
    public void preRemove(){
        this.saleAds.forEach(sale -> sale.setSale_topic(null));
    }

}
