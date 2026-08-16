package com.quickcart.inventoryservice.client;

import com.quickcart.inventoryservice.dto.OrderItemResponse;
import com.quickcart.inventoryservice.dto.OrderSkuResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "order-service")
public interface OrderFeignClient {

    @GetMapping("/api/internal/orders/{orderId}")
    OrderSkuResponse getSkuCodeAndQuantityFromOrderId(
            @PathVariable String orderId);
}
