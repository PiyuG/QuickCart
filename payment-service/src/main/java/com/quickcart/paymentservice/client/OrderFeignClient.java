package com.quickcart.paymentservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "order-service")
public interface OrderFeignClient {
    @PostMapping("/api/order/{orderId}/confirm")
    void confirmedOrder(@PathVariable String orderId);

    @PostMapping("/api/order/{orderId}/fail")
    void failedOrder(@PathVariable String orderId);

}
