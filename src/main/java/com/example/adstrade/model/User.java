package com.example.adstrade.model;

import lombok.Getter;
import lombok.Setter;
import com.example.adstrade.enums.Role;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Collections.singletonList;

@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;

    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Role role;

    private int verified_ads;

    private String description;

    @OneToMany(mappedBy = "user", cascade= CascadeType.ALL)
    private List<PurchaseAd> purchaseAds;

    @OneToMany(mappedBy = "user", cascade= CascadeType.ALL)
    private List<SaleAd> saleAds;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "users_badges",
            joinColumns = {
                    @JoinColumn(name = "user_id", referencedColumnName = "id",
                            nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "badge_id", referencedColumnName = "id",
                            nullable = false, updatable = false)})
    private Set<Badge> badges = new HashSet<>();

    public void addBadgeToUsersList(Badge badge)
    {
        badges.add(badge);
    }


    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_voted_sale_ads",
            joinColumns = {
                    @JoinColumn(name = "user_id", referencedColumnName = "id",
                            nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "sale_id", referencedColumnName = "sale_id",
                            nullable = false, updatable = false)})
    private Set<SaleAd> votedSaleAds = new HashSet<>();

    public void addVotedSaleAd(SaleAd saleAd){
        votedSaleAds.add(saleAd);
    }

}
