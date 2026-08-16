package com.quickcart.inventoryservice.service.impl;

import com.quickcart.inventoryservice.client.OrderFeignClient;
import com.quickcart.inventoryservice.dto.InventoryRequest;
import com.quickcart.inventoryservice.dto.InventoryResponse;
import com.quickcart.inventoryservice.dto.OrderItemResponse;
import com.quickcart.inventoryservice.dto.OrderSkuResponse;
import com.quickcart.inventoryservice.entity.Inventory;
import com.quickcart.inventoryservice.entity.ProcessedOrder;
import com.quickcart.inventoryservice.event.InventoryCommitEvent;
import com.quickcart.inventoryservice.event.OrderPlacedEvent;
import com.quickcart.inventoryservice.exception.InventoryNotFoundException;
import com.quickcart.inventoryservice.mapper.InventoryRequestMapper;
import com.quickcart.inventoryservice.repository.InventoryRepository;
import com.quickcart.inventoryservice.repository.ProcessedOrderRepository;
import com.quickcart.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepo;
    private final InventoryRequestMapper inventoryMapper;
    private final ProcessedOrderRepository processedOrderRepository;
    private final OrderFeignClient orderFeignClient;

    @Override
    public InventoryResponse checkInventory(String skuCode) {
        Inventory inventory=inventoryRepo.findBySkuCode(skuCode).orElseThrow(()->new InventoryNotFoundException(skuCode));
        return inventoryMapper.toDto(inventory);
    }

    @Override
    public void addInventory(InventoryRequest inventoryRequest) {
        Inventory inventory=inventoryMapper.toEntity(inventoryRequest);
        inventoryRepo.save(inventory);
    }

    @Override
    @Transactional
    public void updateStock(OrderPlacedEvent event) {
        if(processedOrderRepository.existsByOrderId(event.getOrderId())){
            return;
        }
        Inventory inventory=inventoryRepo.findBySkuCode(event.getSkuCode()).orElseThrow(()->new InventoryNotFoundException(event.getSkuCode()));
        Integer availableQuantity=inventory.getQuantity();
        Integer orderQuantity= event.getQuantity();
        if(availableQuantity<orderQuantity){
            throw new RuntimeException("Insufficient stock for this skuCode");
        }
        inventory.setReservedStock(inventory.getReservedStock()+orderQuantity);
        inventory.setQuantity(availableQuantity-orderQuantity);
        inventoryRepo.save(inventory);

        ProcessedOrder processedNewOrder=new ProcessedOrder();
        processedNewOrder.setOrderId(event.getOrderId());
        processedNewOrder.setProcessedAt(LocalDateTime.now());
        processedOrderRepository.save(processedNewOrder);
    }

    @Override
    public void commitInventory(InventoryCommitEvent inventoryCommitEvent) {
        OrderSkuResponse order = orderFeignClient.getSkuCodeAndQuantityFromOrderId(inventoryCommitEvent.getOrderId());
        Inventory inventory=inventoryRepo.findBySkuCode(order.getSkuCode()).orElseThrow(()->new InventoryNotFoundException(order.getSkuCode()));
        inventory.setReservedStock(inventory.getReservedStock()-order.getQuantity());
        inventory.setSoldStock(order.getQuantity());
        inventoryRepo.save(inventory);
    }

    @Override
    public void rollbackInventory(InventoryCommitEvent inventoryCommitEvent) {
        OrderSkuResponse order = orderFeignClient.getSkuCodeAndQuantityFromOrderId(inventoryCommitEvent.getOrderId());
        Inventory inventory=inventoryRepo.findBySkuCode(order.getSkuCode()).orElseThrow(()->new InventoryNotFoundException(order.getSkuCode()));
        inventory.setReservedStock(inventory.getReservedStock()-order.getQuantity());
        inventory.setQuantity(inventory.getQuantity()+order.getQuantity());
        inventoryRepo.save(inventory);
    }


}
