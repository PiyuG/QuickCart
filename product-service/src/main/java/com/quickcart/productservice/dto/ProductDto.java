package com.quickcart.productservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {
    @NotBlank(message = "Product name must be required")
    String name;
    @NotBlank
    String description;
    @Positive(message = "price must be positive")
    Double price;
    Double discountPrice;
    @Min(value = 0, message = "Quantity must be 0 or more")
    private int quantity;
    private String brand;
    private String imageUrl;
    private String skuCode;
    @NotNull(message = "category is is required")
    private Long categoryId;
}
