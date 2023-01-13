package com.example.adstrade.model;

import com.example.adstrade.enums.TopicValue;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Sale_TopicMock {

    public static @NotNull Sale_Topic topic() {
        Sale_Topic topic = new Sale_Topic();
        topic.setName(TopicValue.FOOD);
        topic.setId(1L);
        topic.setSaleAds(new ArrayList<>());
        return topic;
    }
}
