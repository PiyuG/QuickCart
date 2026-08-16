package com.quickcart.inventoryservice.consumer;

import com.quickcart.inventoryservice.event.InventoryCommitEvent;
import com.quickcart.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommitInventoryConsumer {
    private final InventoryService inventoryService;
    private final String COMMIT_EVENT="commit-event";

    @KafkaListener(topics = COMMIT_EVENT,groupId = "inventory-group",  containerFactory = "inventoryKafkaListenerContainerFactory")
    public void commitInventory(InventoryCommitEvent inventoryCommitEvent){
        inventoryService.commitInventory(inventoryCommitEvent);
    }
}
