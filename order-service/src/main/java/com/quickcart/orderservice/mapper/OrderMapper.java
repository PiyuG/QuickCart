package com.quickcart.orderservice.mapper;

import com.quickcart.orderservice.dto.OrderRequest;
import com.quickcart.orderservice.dto.OrderResponse;
import com.quickcart.orderservice.entity.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    public Order toEntity(OrderRequest orderRequest){
        Order order=new Order();
        order.setSkuCode(orderRequest.getSkuCode());
        order.setQuantity(orderRequest.getQuantity());
        order.setPrice(orderRequest.getPrice());

        return order;
    }

    public OrderResponse toResponse(Order order){
        return new OrderResponse(order.getOrderNumber(), order.getOrderStatus());
    }
}
