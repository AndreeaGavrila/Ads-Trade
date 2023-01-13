package com.example.adstrade.model.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddBadgeToUsersRequest {
    private Long userId;
    private Long badgeId;
}
