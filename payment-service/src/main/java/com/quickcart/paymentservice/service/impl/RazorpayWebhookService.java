package com.quickcart.paymentservice.service.impl;

import com.quickcart.paymentservice.client.OrderFeignClient;
import com.quickcart.paymentservice.entity.Payment;
import com.quickcart.paymentservice.enums.PaymentStatus;
import com.quickcart.paymentservice.event.InventoryCommitEvent;
import com.quickcart.paymentservice.event.OrderConfirmedEvent;
import com.quickcart.paymentservice.kafka.CommitEventProducer;
import com.quickcart.paymentservice.kafka.NotificationEventProducer;
import com.quickcart.paymentservice.kafka.RollbackEventProducer;
import com.quickcart.paymentservice.repository.PaymentRepository;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RazorpayWebhookService {
    private final PaymentRepository paymentRepository;
    private final OrderFeignClient orderFeignClient;
    private final NotificationEventProducer notificationEventProducer;
    private final CommitEventProducer commitEventProducer;
    private final RollbackEventProducer rollbackEventProducer;

    @Value("${razorpay.webhook-secret}")
    private String webhookSecret;

    public void processWebhook(String signature, String payload){
        verifySignature(signature, payload);
        JSONObject payloadEvent=new JSONObject(payload);
        String eventType=payloadEvent.getString("event");
        log.info("Received RazorpayWebhook event",eventType);
        switch (eventType){
            case "payment.captured" -> handlePaymentSuccess(payloadEvent);
            case "payment.failed" -> handlePaymentFailure(payloadEvent);
            default -> log.warn("Unhandled razorpay event",eventType);
        }
    }

    private void handlePaymentFailure(JSONObject payloadEvent) {
        JSONObject paymentEntity=extractPaymentEntity(payloadEvent);
        String razorpayOrderId=paymentEntity.getString("orderId");
        String razorpayPaymentId=paymentEntity.getString("id");
        Payment payment=paymentRepository.findByTransactionId(razorpayOrderId)
                .orElseThrow(()->new IllegalStateException("Payment not found for Razorpay orderId"));
        payment.setPaymentStatus(PaymentStatus.FAILED);
        payment.setGatewayPaymentId(razorpayPaymentId);
        orderFeignClient.failedOrder(payment.getOrderId());

        InventoryCommitEvent inventoryCommitEvent =new InventoryCommitEvent();
        inventoryCommitEvent.setOrderId(payment.getOrderId());
        rollbackEventProducer.publishRollbackEvent(inventoryCommitEvent);

        OrderConfirmedEvent event=new OrderConfirmedEvent();
        event.setOrderId(payment.getOrderId());
        event.setEmail(paymentEntity.getString("email"));
        event.setMessage("Your order could not be confirmed because the payment failed. Please try again later.");
        notificationEventProducer.publishEvent(event);

        paymentRepository.save(payment);
    }

    private JSONObject extractPaymentEntity(JSONObject event){
        return event.getJSONObject("payload")
                .getJSONObject("payment")
                .getJSONObject("entity");
    }

    private void handlePaymentSuccess(JSONObject payloadEvent) {
        JSONObject paymentEntity=extractPaymentEntity(payloadEvent);
        System.out.println("hii"+paymentEntity.toString(2));
        String razorpayOrderId=paymentEntity.getString("orderId");
        String razorpayPaymentId=paymentEntity.getString("id");
        Payment payment=paymentRepository.findByTransactionId(razorpayOrderId)
                .orElseThrow(()->new IllegalStateException("Payment not found for Razorpay orderId"));
        payment.setPaymentStatus(PaymentStatus.SUCCESS);
        payment.setGatewayPaymentId(razorpayPaymentId);
        orderFeignClient.confirmedOrder(payment.getOrderId());

        OrderConfirmedEvent event=new OrderConfirmedEvent();
        event.setOrderId(payment.getOrderId());
        event.setEmail(paymentEntity.getString("email"));
        event.setMessage("Order confirmed successfully");
        notificationEventProducer.publishEvent(event);

        InventoryCommitEvent inventoryCommitEvent =new InventoryCommitEvent();
        inventoryCommitEvent.setOrderId(payment.getOrderId());
        commitEventProducer.publishInventoryEvent(inventoryCommitEvent);

        paymentRepository.save(payment);

    }

    private void verifySignature(String signature, String payload){
        try {
            Utils.verifyWebhookSignature(payload,signature,webhookSecret);
        } catch (RazorpayException e){
            log.error("Invalid razorpay webhook signature");
            throw new SecurityException("Invalid razorpay webhook signature");
        }
    }


}
