package com.example.adstrade.model.requests;

import lombok.Data;

@Data
public class CloseSaleAdRequest {
    private Long userId;
    private Long saleAdId;
}
