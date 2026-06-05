package com.quickcart.inventoryservice.controller;

import com.quickcart.inventoryservice.dto.InventoryRequest;
import com.quickcart.inventoryservice.dto.InventoryResponse;
import com.quickcart.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping("/{skuCode}")
    public ResponseEntity<InventoryResponse> isInStock(@PathVariable String skuCode){
        return ResponseEntity.ok(inventoryService.checkInventory(skuCode));
    }

    @PostMapping
    public ResponseEntity<String> addInventory(@RequestBody InventoryRequest inventoryRequest){
        inventoryService.addInventory(inventoryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("Inventory Added Successfully.");
    }
}
