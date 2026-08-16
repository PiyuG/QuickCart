package com.quickcart.orderservice.kafka;

import com.quickcart.orderservice.event.OrderPlacedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderEventProducer {
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;
    private  final String ORDER_TOPIC="order-event";
    public OrderEventProducer(KafkaTemplate<String,OrderPlacedEvent> kafkaTemplate){
        this.kafkaTemplate=kafkaTemplate;
    }

    public void sendOderEvent(OrderPlacedEvent event){
        this.kafkaTemplate.send(ORDER_TOPIC,event.getEventId().toString(),event);
    }
}
