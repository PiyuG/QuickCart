package com.quickcart.paymentservice.service.impl;

import com.quickcart.paymentservice.dto.GatewayOrderResponse;
import com.quickcart.paymentservice.dto.PaymentResponse;
import com.quickcart.paymentservice.dto.PaymentStatusUpdatedRequest;
import com.quickcart.paymentservice.dto.RequestPayment;
import com.quickcart.paymentservice.entity.Payment;
import com.quickcart.paymentservice.exception.PaymentNotFoundException;
import com.quickcart.paymentservice.mapper.PaymentMapper;
import com.quickcart.paymentservice.repository.PaymentRepository;
import com.quickcart.paymentservice.service.PaymentGateway;
import com.quickcart.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final PaymentGateway paymentGateway;

    @Override
    public PaymentResponse createPayment(RequestPayment requestPayment) {
        GatewayOrderResponse gatewayOrder =
                paymentGateway.createOrder(
                        requestPayment.getOrderId(),
                        requestPayment.getAmount());

        Payment payment=paymentMapper.toEntity(requestPayment);
        payment.setTransactionId(gatewayOrder.getGatewayOrderId());
        Payment savedPayment=paymentRepository.save(payment);
        return paymentMapper.toDto(savedPayment);
    }

    @Override
    public PaymentResponse getPaymentByOrderId(String orderId) {
        Payment payment=paymentRepository.findByOrderId(orderId).orElseThrow(()-> new PaymentNotFoundException("Payment not found for this orderId"+orderId));
        return paymentMapper.toDto(payment);
    }

    @Override
    public PaymentResponse updatePaymentStatus(Long paymentId, PaymentStatusUpdatedRequest request) {
        Payment payment=paymentRepository.findById(paymentId).orElseThrow(()-> new PaymentNotFoundException("Payment not found with id"+paymentId));
        payment.setPaymentStatus(request.getPaymentStatus());
        paymentRepository.save(payment);
        return paymentMapper.toDto(payment);
    }
}
