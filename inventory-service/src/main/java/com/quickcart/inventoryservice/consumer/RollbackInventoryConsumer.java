package com.quickcart.inventoryservice.consumer;

import com.quickcart.inventoryservice.event.InventoryCommitEvent;
import com.quickcart.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RollbackInventoryConsumer {
    private final InventoryService inventoryService;
    private final String ROLLBACK_EVENT="rollback-event";

    @KafkaListener(topics = ROLLBACK_EVENT,groupId = "inventory-group", containerFactory = "inventoryKafkaListenerContainerFactory")
    public void rollbackInventory(InventoryCommitEvent inventoryCommitEvent){
        inventoryService.rollbackInventory(inventoryCommitEvent);
    }
}
