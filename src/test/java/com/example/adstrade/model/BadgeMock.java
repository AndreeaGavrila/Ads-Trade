package com.example.adstrade.model;

import com.example.adstrade.enums.BadgeType;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class BadgeMock {

    public static @NotNull Badge badge() {
        Badge badge = new Badge();
        badge.setName(BadgeType.BRONZE);
        badge.setId(1L);
        badge.setPoints(14);
        badge.setUsers(new HashSet<>());
        return badge;
    }

}
