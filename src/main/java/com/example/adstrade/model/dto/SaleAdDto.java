package com.example.adstrade.model.dto;

import com.example.adstrade.model.SaleAd;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
@Data
public class SaleAdDto {

    private String message;
    private String date;

    private Long likes;
    private Long dislikes;

    @Enumerated(EnumType.STRING)
    private String status;

    private Long userId;
    private Long purchaseAdId;

    private String sale_topic;

    private List<NotificationDto> notifications;

    public static @NotNull SaleAdDto convertEntityToDto(@NotNull SaleAd saleAd){
        SaleAdDto dto = new SaleAdDto();
        dto.setMessage(saleAd.getMessage());
        dto.setDate(saleAd.getDate());
        dto.setLikes(saleAd.getLikes());
        dto.setDislikes(saleAd.getDislikes());
        dto.setStatus(saleAd.getStatus().toString());

        dto.setUserId(saleAd.getUser().getId());
        dto.setSale_topic(saleAd.getSale_topic().getName().toString());

        if(saleAd.getNotifications() != null){
            dto.setNotifications(saleAd.getNotifications().stream().map(
                    notification -> new NotificationDto(
                            notification.getNotification_type().toString(),
                            notification.getNotification_status(),
                            notification.getPurchaseAd().getPurchase_id(),
                            notification.getSaleAd().getSale_id()
                    )).collect(Collectors.toList()));
        }

        dto.setPurchaseAdId(saleAd.getPurchaseAd().getPurchase_id());
        return dto;
    }

}
