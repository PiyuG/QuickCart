package com.quickcart.orderservice.service;

import com.quickcart.orderservice.dto.OrderRequest;
import com.quickcart.orderservice.dto.OrderResponse;
import com.quickcart.orderservice.dto.OrderSkuResponse;

public interface OrderService {
    OrderResponse placeOrder(OrderRequest orderRequest, String userId);
    void confirmedOrder(String orderId);
    void failOrder(String orderId);

    OrderSkuResponse getSkuCodeAndQuantity(String orderId);
}
