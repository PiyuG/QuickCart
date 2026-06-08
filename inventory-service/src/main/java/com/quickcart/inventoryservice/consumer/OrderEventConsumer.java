package com.quickcart.inventoryservice.consumer;

import com.quickcart.inventoryservice.event.OrderPlacedEvent;
import com.quickcart.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderEventConsumer {
    private final InventoryService inventoryService;
    @KafkaListener(topics = "order-event",groupId = "inventory-group")
    public void consumer(OrderPlacedEvent event){
        inventoryService.updateStock(event);
    }
}
