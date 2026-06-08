package com.quickcart.inventoryservice.entity;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document("processed_order")
public class ProcessedOrder {
    @Id
    private String id;

    @Indexed(unique = true)
    private String orderId;
    private LocalDateTime processedAt;
}
