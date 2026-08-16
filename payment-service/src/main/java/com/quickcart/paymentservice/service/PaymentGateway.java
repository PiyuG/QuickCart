package com.quickcart.paymentservice.service;

import com.quickcart.paymentservice.dto.GatewayOrderResponse;

import java.math.BigDecimal;

public interface PaymentGateway {
    GatewayOrderResponse createOrder(String orderId, BigDecimal amount);
}
