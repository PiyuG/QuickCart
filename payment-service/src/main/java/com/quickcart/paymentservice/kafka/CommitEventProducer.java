package com.quickcart.paymentservice.kafka;

import com.quickcart.paymentservice.event.InventoryCommitEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommitEventProducer {
    private final KafkaTemplate<String, InventoryCommitEvent> kafkaTemplate;
    private final String COMMIT_EVENT="commit-event";


    public void publishInventoryEvent(InventoryCommitEvent inventoryCommitEvent){
        System.out.println("=================================");
        System.out.println("PUBLISHING COMMIT EVENT");
        System.out.println("Class = " + inventoryCommitEvent.getClass().getName());
        System.out.println("OrderId = " + inventoryCommitEvent.getOrderId());
        System.out.println("Topic = " + COMMIT_EVENT);
        System.out.println("=================================");
        this.kafkaTemplate.send(COMMIT_EVENT, inventoryCommitEvent);
    }

}
