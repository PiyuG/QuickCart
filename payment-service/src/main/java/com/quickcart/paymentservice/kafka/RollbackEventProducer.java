package com.quickcart.paymentservice.kafka;

import com.quickcart.paymentservice.event.InventoryCommitEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RollbackEventProducer {

    private final KafkaTemplate<String, InventoryCommitEvent> kafkaTemplate;
    private final String ROLLBACK_EVENT="rollback-event";

    public void publishRollbackEvent(InventoryCommitEvent inventoryCommitEvent){
        this.kafkaTemplate.send(ROLLBACK_EVENT, inventoryCommitEvent);
    }
}
