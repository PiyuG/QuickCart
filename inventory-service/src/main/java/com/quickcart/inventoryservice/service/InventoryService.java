package com.quickcart.inventoryservice.service;

import com.quickcart.inventoryservice.dto.InventoryRequest;
import com.quickcart.inventoryservice.dto.InventoryResponse;
import com.quickcart.inventoryservice.event.InventoryCommitEvent;
import com.quickcart.inventoryservice.event.OrderPlacedEvent;

public interface InventoryService {
    public InventoryResponse checkInventory(String skuCode);

    public void addInventory(InventoryRequest inventoryRequest);

    public void updateStock(OrderPlacedEvent event);

    public void commitInventory(InventoryCommitEvent inventoryCommitEvent);

    public void rollbackInventory(InventoryCommitEvent inventoryCommitEvent);
}
