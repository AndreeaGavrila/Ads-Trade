package com.example.adstrade.model.dto;

import com.example.adstrade.model.Purchase_Topic;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class PurchaseTopicDto {

    @Enumerated(EnumType.STRING)
    private String name;

    public static @NotNull PurchaseTopicDto  convertEntityToDto (@NotNull Purchase_Topic topic){
        PurchaseTopicDto dto = new PurchaseTopicDto();
        dto.setName(topic.getName().toString());
        return dto;
    }
}
