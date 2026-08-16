package com.quickcart.orderservice.service.impl;

import com.quickcart.orderservice.client.InventoryFeignClient;
import com.quickcart.orderservice.dto.InventoryResponse;
import com.quickcart.orderservice.dto.OrderRequest;
import com.quickcart.orderservice.dto.OrderResponse;
import com.quickcart.orderservice.dto.OrderSkuResponse;
import com.quickcart.orderservice.entity.Order;
import com.quickcart.orderservice.event.OrderPlacedEvent;
import com.quickcart.orderservice.kafka.OrderEventProducer;
import com.quickcart.orderservice.mapper.OrderMapper;
import com.quickcart.orderservice.repository.OrderRepository;
import com.quickcart.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final InventoryFeignClient inventoryFeignClient;
    private final OrderEventProducer orderEventProducer;

    @Override
    public OrderResponse placeOrder(OrderRequest orderRequest,String userId) {
        Order order=orderMapper.toEntity(orderRequest);
        InventoryResponse response=inventoryFeignClient.isInStock(orderRequest.getSkuCode());
        if(!response.isInStock()){
            throw new RuntimeException("Product is out of stock");
        }
        String orderId=UUID.randomUUID().toString();
        order.setOrderNumber(orderId);
        order.setUserId(userId);
        order.setOrderStatus("CREATED");
        orderRepository.save(order);

        OrderPlacedEvent event=new OrderPlacedEvent();
        event.setEventId(UUID.randomUUID().toString());
        event.setOrderId(orderId);
        event.setSkuCode(orderRequest.getSkuCode());
        event.setQuantity(orderRequest.getQuantity());
        event.setEvenTime(LocalDateTime.now());
        orderEventProducer.sendOderEvent(event);

        return orderMapper.toResponse(order);
    }

    @Override
    public void confirmedOrder(String orderId) {
        Order order=orderRepository.findByOrderNumber(orderId).orElseThrow(()-> new RuntimeException("OrderId not found"));
        order.setOrderStatus("CONFIRMED");
        orderRepository.save(order);

    }

    @Override
    public void failOrder(String orderId) {
        Order order=orderRepository.findByOrderNumber(orderId).orElseThrow(()->new RuntimeException("OrderId not found"));
        order.setOrderStatus("FAILED");
        orderRepository.save(order);
    }

    @Override
    public OrderSkuResponse getSkuCodeAndQuantity(String orderId) {
        Order order=orderRepository.findByOrderNumber(orderId).orElseThrow(()->new RuntimeException("OrderId not found"));
        return orderMapper.toSkuResponse(order);
    }
}
