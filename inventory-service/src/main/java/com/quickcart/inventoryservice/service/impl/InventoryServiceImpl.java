package com.quickcart.inventoryservice.service.impl;

import com.quickcart.inventoryservice.dto.InventoryRequest;
import com.quickcart.inventoryservice.dto.InventoryResponse;
import com.quickcart.inventoryservice.entity.Inventory;
import com.quickcart.inventoryservice.exception.InventoryNotFoundException;
import com.quickcart.inventoryservice.mapper.InventoryRequestMapper;
import com.quickcart.inventoryservice.repository.InventoryRepository;
import com.quickcart.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepo;
    private final InventoryRequestMapper inventoryMapper;


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
}
