package com.example.adstrade.model.dto;

import com.example.adstrade.model.Sale_Topic;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class SaleTopicDto {

    @Enumerated(EnumType.STRING)
    private String name;

    public static @NotNull SaleTopicDto convertEntityToDto (@NotNull Sale_Topic topic){
        SaleTopicDto dto = new SaleTopicDto();
        dto.setName(topic.getName().toString());
        return dto;
    }
}
