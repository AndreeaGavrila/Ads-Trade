package com.example.adstrade.repository;

import com.example.adstrade.model.PurchaseAd;
import com.example.adstrade.model.SaleAd;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleAdRepository extends JpaRepository<SaleAd, Long> {

    @Modifying(clearAutomatically = true)
    @Query("DELETE from SaleAd q WHERE q.sale_id = ?1")
    void deleteById(@NotNull Long id);


   List<SaleAd> findByPurchaseAd(PurchaseAd q);

}
