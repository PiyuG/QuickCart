package com.quickcart.inventoryservice.service;

import com.quickcart.inventoryservice.dto.InventoryRequest;
import com.quickcart.inventoryservice.dto.InventoryResponse;
import com.quickcart.inventoryservice.event.OrderPlacedEvent;

public interface InventoryService {
    public InventoryResponse checkInventory(String skuCode);

    public void addInventory(InventoryRequest inventoryRequest);

    void updateStock(OrderPlacedEvent event);
}
