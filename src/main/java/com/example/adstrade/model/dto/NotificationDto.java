package com.example.adstrade.model.dto;

import com.example.adstrade.enums.NotificationStatus;
import lombok.Data;

@Data
public class NotificationDto {
    private String notification_type;
    private String notification_status;
    private Long purchase_id;
    private Long sale_id;

    public NotificationDto(String notification_type, NotificationStatus notification_status,
                           Long purchase_id, Long sale_id) {
        this.notification_type = notification_type;
        this.notification_status = notification_status.toString();
        this.purchase_id = purchase_id;
        this.sale_id = sale_id;
    }

    public NotificationDto(String notification_type, NotificationStatus notification_status,
                           Long purchase_id) {
        this.notification_type = notification_type;
        this.notification_status = notification_status.toString();
        this.purchase_id = purchase_id;
    }

}
