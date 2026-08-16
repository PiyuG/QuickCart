package com.quickcart.inventoryservice.repository;

import com.quickcart.inventoryservice.entity.ProcessedOrder;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessedOrderRepository extends MongoRepository<ProcessedOrder,String> {
    boolean existsByOrderId(String orderId);

}
