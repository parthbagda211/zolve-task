package com.example.zolve.enums;

import lombok.Getter;

@Getter
public enum SubscriptionType {
    SILVER(7),   // weekly subscription
    GOLD(30),    // monthly subscription
    PLATINUM(365); // yearly subscription

    private final int durationInDays;

    SubscriptionType(int durationInDays) {
        this.durationInDays = durationInDays;
    }

}