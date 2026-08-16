package com.quickcart.inventoryservice.mapper;

import com.quickcart.inventoryservice.dto.InventoryRequest;
import com.quickcart.inventoryservice.dto.InventoryResponse;
import com.quickcart.inventoryservice.entity.Inventory;
import org.springframework.stereotype.Component;

@Component
public class InventoryRequestMapper {
    public Inventory toEntity(InventoryRequest inventoryRequest){
        Inventory inventory=new Inventory();
        inventory.setSkuCode(inventoryRequest.getSkuCode());
        inventory.setQuantity(inventoryRequest.getQuantity());
        return inventory;
    }

    public InventoryResponse toDto(Inventory inventory){
        return new InventoryResponse(inventory.getSkuCode(),inventory.getQuantity()>0,inventory.getQuantity());
    }
}
