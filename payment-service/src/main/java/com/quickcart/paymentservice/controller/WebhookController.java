package com.quickcart.paymentservice.controller;

import com.quickcart.paymentservice.service.impl.RazorpayWebhookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class WebhookController {

    private final RazorpayWebhookService webhookService;

    @PostMapping("/webhook/razorpay")
    public ResponseEntity<Void> handleWebhook(@RequestHeader("X-Razorpay-Signature") String signature, @RequestBody String payload){
        webhookService.processWebhook(signature, payload);
        return ResponseEntity.ok().build();
    }
}
