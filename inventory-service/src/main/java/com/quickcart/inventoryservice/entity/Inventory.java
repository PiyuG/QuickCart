package com.quickcart.inventoryservice.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document("inventory")
public class Inventory {
    @Id
    private String id;

    @NotBlank
    @Indexed(unique = true)
    private String skuCode;

    @NotNull
    private Integer quantity;

    private Integer reservedStock=0;

    private Integer soldStock=0;

}
