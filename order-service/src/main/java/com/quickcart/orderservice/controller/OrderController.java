package com.quickcart.orderservice.controller;

import com.quickcart.orderservice.dto.OrderRequest;
import com.quickcart.orderservice.dto.OrderResponse;
import com.quickcart.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest orderRequest){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String userId=authentication.getName();
        OrderResponse response=orderService.placeOrder(orderRequest, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{orderId}/confirm")
    public void confirmOrder(@PathVariable String orderId){
        orderService.confirmedOrder(orderId);
    }

    @PostMapping("/{orderId}/fail")
    public void failOrder(@PathVariable String orderId){
        orderService.failOrder(orderId);
    }

}
