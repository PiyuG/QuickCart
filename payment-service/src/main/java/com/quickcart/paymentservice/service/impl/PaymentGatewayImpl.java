package com.quickcart.paymentservice.service.impl;

import com.quickcart.paymentservice.dto.GatewayOrderResponse;
import com.quickcart.paymentservice.service.PaymentGateway;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PaymentGatewayImpl implements PaymentGateway {
    private final RazorpayClient razorpayClient;

    @Override
    public GatewayOrderResponse createOrder(String orderId, BigDecimal amount) {
        try {
            JSONObject options = new JSONObject();
            options.put("amount", amount.multiply(BigDecimal.valueOf(100)));
            options.put("currency", "INR");
            options.put("receipt", orderId);
            Order order = razorpayClient.orders.create(options);

            return new GatewayOrderResponse(
                    order.get("id"),
                    order.get("currency"),
                    amount,
                    order.get("status"),
                    "RAZORPAY"
            );
        }catch (RazorpayException e) {
                e.printStackTrace();
                throw new IllegalStateException("Razorpay order creation failed: " + e.getMessage(), e);
            }

    }
}
