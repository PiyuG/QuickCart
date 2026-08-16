package com.quickcart.inventoryservice.dto;

import lombok.Data;

@Data
public class OrderSkuResponse {
    private String skuCode;
    private Integer quantity;
}
