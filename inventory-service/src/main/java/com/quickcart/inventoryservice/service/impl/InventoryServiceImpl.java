package com.quickcart.inventoryservice.service.impl;

import com.quickcart.inventoryservice.dto.InventoryRequest;
import com.quickcart.inventoryservice.dto.InventoryResponse;
import com.quickcart.inventoryservice.entity.Inventory;
import com.quickcart.inventoryservice.entity.ProcessedOrder;
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
        if(processedOrderRepository.existByOrderId(event.getOrderId())){
            return;
        }
        Inventory inventory=inventoryRepo.findBySkuCode(event.getSkuCode()).orElseThrow(()->new InventoryNotFoundException(event.getSkuCode()));
        Integer availableQuantity=inventory.getQuantity();
        Integer orderQuantity= event.getQuantity();
        if(availableQuantity<orderQuantity){
            throw new RuntimeException("Insufficient stock for this skuCode");
        }
        inventory.setQuantity(availableQuantity-orderQuantity);
        inventoryRepo.save(inventory);
        ProcessedOrder processedNewOrder=new ProcessedOrder();
        processedNewOrder.setOrderId(event.getOrderId());
        processedNewOrder.setProcessedAt(LocalDateTime.now());
        processedOrderRepository.save(processedNewOrder);
    }
}
