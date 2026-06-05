package com.quickcart.orderservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    @NotBlank(message = "skuCode is not null")
    private String skuCode;

    @Min(value = 1, message = "quantity must be at least 1")
    private Integer quantity;

    @NotNull(message = "price is mandatory")
    private Double price;
}
