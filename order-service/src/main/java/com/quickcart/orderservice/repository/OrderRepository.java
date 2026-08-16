package com.quickcart.orderservice.repository;

import com.quickcart.orderservice.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends MongoRepository<Order,String> {
    Optional<Order> findByOrderNumber(String s);
}
