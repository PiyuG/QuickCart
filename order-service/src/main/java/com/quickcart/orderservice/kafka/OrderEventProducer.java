package com.quickcart.orderservice.kafka;

import com.quickcart.orderservice.event.OrderPlacedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.event.KafkaEvent;
import org.springframework.stereotype.Service;

@Service
public class OrderEventProducer {
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public OrderEventProducer(KafkaTemplate<String,OrderPlacedEvent> kafkaTemplate){
        this.kafkaTemplate=kafkaTemplate;
    }

    public void sendOderEvent(OrderPlacedEvent event){
        kafkaTemplate.send("order-event",event.getEventId().toString(),event);

    }
}
