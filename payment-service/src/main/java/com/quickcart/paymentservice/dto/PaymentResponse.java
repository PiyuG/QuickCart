package com.quickcart.paymentservice.dto;

import com.quickcart.paymentservice.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {
    private Long paymentId;
    private String orderId;
    private BigDecimal amount;
    private PaymentStatus paymentStatus;
    private String transactionId;
}
