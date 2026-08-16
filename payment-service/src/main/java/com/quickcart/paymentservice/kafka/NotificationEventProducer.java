package com.quickcart.paymentservice.kafka;

import com.quickcart.paymentservice.event.OrderConfirmedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationEventProducer {
    private final KafkaTemplate<String, OrderConfirmedEvent> kafkaTemplate;
    private final String ORDER_CONFIRMED_TOPIC="order-confirmed-event";
    public NotificationEventProducer(KafkaTemplate<String,OrderConfirmedEvent> kafkaTemplate){
        this.kafkaTemplate=kafkaTemplate;
    }

    public void publishEvent(OrderConfirmedEvent event){
        this.kafkaTemplate.send(ORDER_CONFIRMED_TOPIC,event);

    }
}
