package com.quickcart.inventoryservice.service;

import com.quickcart.inventoryservice.dto.InventoryRequest;
import com.quickcart.inventoryservice.dto.InventoryResponse;

public interface InventoryService {
    public InventoryResponse checkInventory(String skuCode);

    public void addInventory(InventoryRequest inventoryRequest);
}
