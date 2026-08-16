package com.quickcart.orderservice.controller;

import com.quickcart.orderservice.dto.OrderSkuResponse;
import com.quickcart.orderservice.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/internal/orders")
public class InternalOrderController {

    private final OrderService orderService;

    public InternalOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{orderId}")
    public OrderSkuResponse getSkuCodeAndQuantity(@PathVariable String orderId) {

        return orderService.getSkuCodeAndQuantity(orderId);
    }
}
