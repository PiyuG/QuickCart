package com.quickcart.notificationservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderConfirmedEvent {

    private String orderId;
    private String email;
    private String message;
}
