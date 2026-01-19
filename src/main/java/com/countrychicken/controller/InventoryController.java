package com.countrychicken.controller;

import com.countrychicken.dto.InventoryDTO;
import com.countrychicken.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
@Tag(name = "Inventory Management", description = "APIs for managing inventory")
public class InventoryController {
    
    private final InventoryService inventoryService;
    
    @PostMapping
    @Operation(summary = "Add a new inventory item")
    public ResponseEntity<InventoryDTO> addInventoryItem(@RequestBody InventoryDTO.InventoryRequest request) {
        InventoryDTO createdItem = inventoryService.addInventoryItem(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);
    }
    
    @GetMapping
    @Operation(summary = "Get all inventory items")
    public ResponseEntity<List<InventoryDTO>> getAllInventory() {
        List<InventoryDTO> inventory = inventoryService.getAllInventory();
        return ResponseEntity.ok(inventory);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get inventory item by ID")
    public ResponseEntity<InventoryDTO> getInventoryById(@PathVariable Long id) {
        InventoryDTO item = inventoryService.getInventoryById(id);
        return ResponseEntity.ok(item);
    }
    
    @GetMapping("/code/{itemCode}")
    @Operation(summary = "Get inventory item by item code")
    public ResponseEntity<InventoryDTO> getInventoryByCode(@PathVariable String itemCode) {
        InventoryDTO item = inventoryService.getInventoryByCode(itemCode);
        return ResponseEntity.ok(item);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update inventory item")
    public ResponseEntity<InventoryDTO> updateInventoryItem(
            @PathVariable Long id,
            @RequestBody InventoryDTO.InventoryRequest request) {
        InventoryDTO updatedItem = inventoryService.updateInventoryItem(id, request);
        return ResponseEntity.ok(updatedItem);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an inventory item")
    public ResponseEntity<Void> deleteInventoryItem(@PathVariable Long id) {
        inventoryService.deleteInventoryItem(id);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/{id}/restock")
    @Operation(summary = "Restock inventory item")
    public ResponseEntity<InventoryDTO> restockInventory(
            @PathVariable Long id,
            @RequestBody InventoryDTO.RestockRequest request) {
        InventoryDTO restockedItem = inventoryService.restockInventory(id, request);
        return ResponseEntity.ok(restockedItem);
    }
    
    @PostMapping("/consume/{itemCode}/{quantity}")
    @Operation(summary = "Consume inventory item")
    public ResponseEntity<InventoryDTO> consumeInventory(
            @PathVariable String itemCode,
            @PathVariable Integer quantity) {
        InventoryDTO updatedItem = inventoryService.consumeInventory(itemCode, quantity);
        return ResponseEntity.ok(updatedItem);
    }
    
    @GetMapping("/low-stock")
    @Operation(summary = "Get low stock items")
    public ResponseEntity<List<InventoryDTO>> getLowStockItems() {
        List<InventoryDTO> items = inventoryService.getLowStockItems();
        return ResponseEntity.ok(items);
    }
    
    @GetMapping("/out-of-stock")
    @Operation(summary = "Get out of stock items")
    public ResponseEntity<List<InventoryDTO>> getOutOfStockItems() {
        List<InventoryDTO> items = inventoryService.getOutOfStockItems();
        return ResponseEntity.ok(items);
    }
    
    @GetMapping("/category/{category}")
    @Operation(summary = "Get inventory by category")
    public ResponseEntity<List<InventoryDTO>> getInventoryByCategory(@PathVariable String category) {
        List<InventoryDTO> items = inventoryService.getInventoryByCategory(category);
        return ResponseEntity.ok(items);
    }
    
    @GetMapping("/part/{chickenPart}")
    @Operation(summary = "Get inventory by chicken part")
    public ResponseEntity<List<InventoryDTO>> getInventoryByChickenPart(@PathVariable String chickenPart) {
        List<InventoryDTO> items = inventoryService.getInventoryByChickenPart(chickenPart);
        return ResponseEntity.ok(items);
    }
    
    @GetMapping("/available")
    @Operation(summary = "Get available inventory items")
    public ResponseEntity<List<InventoryDTO>> getAvailableInventory() {
        List<InventoryDTO> items = inventoryService.getAvailableInventory();
        return ResponseEntity.ok(items);
    }
    
    @GetMapping("/value/total")
    @Operation(summary = "Get total inventory value")
    public ResponseEntity<Double> getTotalInventoryValue() {
        Double value = inventoryService.getTotalInventoryValue();
        return ResponseEntity.ok(value);
    }
    
    @GetMapping("/supplier/{supplier}")
    @Operation(summary = "Get inventory by supplier")
    public ResponseEntity<List<InventoryDTO>> getInventoryBySupplier(@PathVariable String supplier) {
        List<InventoryDTO> items = inventoryService.getInventoryBySupplier(supplier);
        return ResponseEntity.ok(items);
    }
}