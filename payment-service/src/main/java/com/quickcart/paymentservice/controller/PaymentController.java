package com.quickcart.paymentservice.controller;

import com.quickcart.paymentservice.dto.PaymentResponse;
import com.quickcart.paymentservice.dto.PaymentStatusUpdatedRequest;
import com.quickcart.paymentservice.dto.RequestPayment;
import com.quickcart.paymentservice.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(@RequestBody @Valid RequestPayment requestPayment){
        PaymentResponse response=paymentService.createPayment(requestPayment);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/getPayment/{orderId}")
    public ResponseEntity<PaymentResponse> getPayment(@PathVariable String orderId){
        PaymentResponse response=paymentService.getPaymentByOrderId(orderId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{paymentId}/status")
    public ResponseEntity<PaymentResponse> updateStatus(@PathVariable Long paymentId, @RequestBody PaymentStatusUpdatedRequest request){
        PaymentResponse response=paymentService.updatePaymentStatus(paymentId,request);
        return ResponseEntity.ok(response);
    }
}
