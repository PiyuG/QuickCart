package com.quickcart.orderservice.service.impl;

import com.quickcart.orderservice.client.InventoryFeignClient;
import com.quickcart.orderservice.dto.InventoryResponse;
import com.quickcart.orderservice.dto.OrderRequest;
import com.quickcart.orderservice.dto.OrderResponse;
import com.quickcart.orderservice.entity.Order;
import com.quickcart.orderservice.mapper.OrderMapper;
import com.quickcart.orderservice.repository.OrderRepository;
import com.quickcart.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final InventoryFeignClient inventoryFeignClient;

    @Override
    public OrderResponse placeOrder(OrderRequest orderRequest) {
        Order order=orderMapper.toEntity(orderRequest);
        InventoryResponse response=inventoryFeignClient.isInStock(orderRequest.getSkuCode());
        if(!response.isInStock()){
            throw new RuntimeException("Product is out of stock");
        }
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setOrderStatus("CREATED");
        orderRepository.save(order);
        return orderMapper.toResponse(order);
    }
}
