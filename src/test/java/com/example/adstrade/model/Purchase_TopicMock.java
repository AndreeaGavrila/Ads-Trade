package com.example.adstrade.model;

import com.example.adstrade.enums.TopicValue;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Purchase_TopicMock {

    public static @NotNull Purchase_Topic topic() {
        Purchase_Topic topic = new Purchase_Topic();
        topic.setName(TopicValue.FOOD);
        topic.setId(1L);
        topic.setPurchaseAds(new ArrayList<>());
        return topic;
    }
}
