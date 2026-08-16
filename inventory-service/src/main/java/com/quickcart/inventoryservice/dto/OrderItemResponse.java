package com.quickcart.inventoryservice.dto;

import lombok.Data;

@Data
public class OrderItemResponse {
    private String orderNumber;
    private String orderStatus;
    private String skuCode;
    private Integer quantity;
}
