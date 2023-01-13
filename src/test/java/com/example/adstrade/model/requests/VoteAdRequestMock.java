package com.example.adstrade.model.requests;

import org.jetbrains.annotations.NotNull;

public class VoteAdRequestMock {

    public static @NotNull VoteAdRequest voteAdRequest() {
        VoteAdRequest voteAdRequest = new VoteAdRequest();
        voteAdRequest.setUserId(1L);
        voteAdRequest.setAdId(1L);
        voteAdRequest.setOption(1);
        return voteAdRequest;
    }
}
