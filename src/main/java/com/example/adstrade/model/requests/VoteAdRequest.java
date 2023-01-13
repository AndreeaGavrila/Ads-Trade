package com.example.adstrade.model.requests;

import lombok.Data;

@Data
public class VoteAdRequest {
    private Long adId;
    private Long userId;
    private int option;
}
