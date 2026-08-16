package com.quickcart.paymentservice.mapper;

import com.quickcart.paymentservice.dto.PaymentResponse;
import com.quickcart.paymentservice.dto.RequestPayment;
import com.quickcart.paymentservice.entity.Payment;
import com.quickcart.paymentservice.enums.PaymentStatus;
import com.quickcart.paymentservice.util.TransactionGenerator;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {
    public Payment toEntity(RequestPayment request){
        return Payment.builder()
                .orderId(request.getOrderId())
                .amount(request.getAmount())
                .paymentMethod(request.getPaymentMethod())
                .paymentStatus(PaymentStatus.PENDING)
                .transactionId(TransactionGenerator.generate())
                .build();
    }

    public PaymentResponse toDto(Payment payment){
        return PaymentResponse.builder()
                .paymentId(payment.getId())
                .orderId(payment.getOrderId())
                .amount(payment.getAmount())
                .paymentStatus(payment.getPaymentStatus())
                .transactionId(payment.getTransactionId())
                .build();
    }
}
