package com.example.adstrade.model.dto;

import com.example.adstrade.enums.TopicValue;
import org.jetbrains.annotations.NotNull;

public class SaleTopicDtoMock {

    public static @NotNull SaleTopicDto topic() {
        SaleTopicDto dto = new SaleTopicDto();
        dto.setName(TopicValue.FOOD.toString());
        return dto;
    }
}
