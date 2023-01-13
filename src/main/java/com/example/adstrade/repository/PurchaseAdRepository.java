package com.example.adstrade.repository;

import com.example.adstrade.model.PurchaseAd;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseAdRepository extends JpaRepository<PurchaseAd, Long> {

    @Modifying(clearAutomatically = true)
    @Query("DELETE from PurchaseAd q WHERE q.purchase_id = ?1")
    void deleteById(@NotNull Long id);

}