package com.example.adstrade.model.requests;

import lombok.Data;

@Data
public class MarkCompleteAdRequest {
    private Long userId;
    private Long purchaseAdId;
    private Long saleAdId;
}
