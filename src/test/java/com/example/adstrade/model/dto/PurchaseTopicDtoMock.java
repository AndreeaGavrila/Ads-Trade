package com.example.adstrade.model.dto;

import com.example.adstrade.enums.TopicValue;
import org.jetbrains.annotations.NotNull;


public class PurchaseTopicDtoMock {

    public static @NotNull PurchaseTopicDto topic() {
        PurchaseTopicDto dto = new PurchaseTopicDto();
        dto.setName(TopicValue.FOOD.toString());
        return dto;
    }
}
