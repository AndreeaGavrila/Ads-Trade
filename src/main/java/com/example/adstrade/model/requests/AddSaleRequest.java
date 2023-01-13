package com.example.adstrade.model.requests;

import lombok.Data;

@Data
public class AddSaleRequest {
    private Long purchaseAdId;
    private Long userId;
    private String message;
}
