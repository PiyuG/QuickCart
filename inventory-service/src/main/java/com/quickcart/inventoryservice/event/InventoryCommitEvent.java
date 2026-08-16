package com.quickcart.inventoryservice.event;

import lombok.Data;

@Data
public class InventoryCommitEvent {
    private String orderId;
}
