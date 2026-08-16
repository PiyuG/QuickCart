package com.quickcart.notificationservice.consumer;

import com.quickcart.notificationservice.event.OrderConfirmedEvent;
import com.quickcart.notificationservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationConsumer {
    private final EmailService emailService;
    private final String ORDER_CONFIRMED_TOPIC="order-confirmed-event";

    @KafkaListener(topics = ORDER_CONFIRMED_TOPIC,groupId = "notification-group")
    public void consume(OrderConfirmedEvent event){
        emailService.sendMail( event.getEmail(),
                "Order Update - " + event.getOrderId(),
                event.getMessage());
    }
}
