package com.quickcart.inventoryservice.repository;

import com.quickcart.inventoryservice.entity.ProcessedOrder;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProcessedOrderRepository extends MongoRepository<ProcessedOrder,String> {
    boolean existByOrderId(String orderId);

}
