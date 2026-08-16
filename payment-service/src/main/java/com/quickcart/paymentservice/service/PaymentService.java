package com.quickcart.paymentservice.service;

import com.quickcart.paymentservice.dto.PaymentResponse;
import com.quickcart.paymentservice.dto.PaymentStatusUpdatedRequest;
import com.quickcart.paymentservice.dto.RequestPayment;

public interface PaymentService {
    PaymentResponse createPayment(RequestPayment requestPayment);
    PaymentResponse getPaymentByOrderId(String orderId);
    PaymentResponse updatePaymentStatus(Long paymentId, PaymentStatusUpdatedRequest request);
}
