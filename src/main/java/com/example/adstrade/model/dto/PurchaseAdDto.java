package com.example.adstrade.model.dto;

import com.example.adstrade.model.PurchaseAd;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Data
public class PurchaseAdDto {

    @NotBlank(message = "Message is mandatory")
    private String message;

    private String date;
    private boolean completed;

    private Long userId;

    private String purchase_topic;

    private List<NotificationDto> notifications;
    private List<SaleAdDto> saleAds;

    public static PurchaseAdDto convertEntityToDto(PurchaseAd purchaseAd){
        PurchaseAdDto dto = new PurchaseAdDto();
        dto.setMessage(purchaseAd.getMessage());
        dto.setDate(purchaseAd.getDate());
        dto.setCompleted(purchaseAd.isCompleted());

        dto.setUserId(purchaseAd.getUser().getId());
        dto.setPurchase_topic(purchaseAd.getPurchase_topic().getName().toString());

        if(purchaseAd.getNotifications() != null){
            dto.setNotifications(purchaseAd.getNotifications().stream().map(
                    notification -> new NotificationDto(
                            notification.getNotification_type().toString(),
                            notification.getNotification_status(),
                            notification.getPurchaseAd().getPurchase_id(),
                            notification.getSaleAd().getSale_id()
                    )).collect(Collectors.toList()));
        }

        if(purchaseAd.getSaleAds() !=null){
            dto.setSaleAds(purchaseAd.getSaleAds().stream().map(SaleAdDto::convertEntityToDto).collect(toList()));
        }
        return dto;
    }

}
