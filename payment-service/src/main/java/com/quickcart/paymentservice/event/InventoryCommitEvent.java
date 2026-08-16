package com.quickcart.paymentservice.event;

import lombok.Data;

@Data
public class InventoryCommitEvent {
    private String orderId;
}
