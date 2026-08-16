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
    private  final String ORDER_TOPIC="order-event";

    @KafkaListener(topics =ORDER_TOPIC,groupId = "inventory-group", containerFactory = "orderKafkaListenerContainerFactory")
    public void consumer(OrderPlacedEvent orderPlacedEvent) {
        inventoryService.updateStock(orderPlacedEvent);
    }
}
