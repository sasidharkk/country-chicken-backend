package com.countrychicken.service;

import com.countrychicken.dto.InventoryDTO;
import com.countrychicken.entity.Inventory;
import com.countrychicken.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {
    
    private final InventoryRepository inventoryRepository;
    
    @Transactional
    public InventoryDTO addInventoryItem(InventoryDTO.InventoryRequest request) {
        log.info("Adding new inventory item: {}", request.getItemName());
        
        // Check if item code already exists
        if (inventoryRepository.findByItemCode(request.getItemCode()).isPresent()) {
            throw new RuntimeException("Item code already exists: " + request.getItemCode());
        }
        
        Inventory inventory = new Inventory();
        inventory.setItemCode(request.getItemCode());
        inventory.setItemName(request.getItemName());
        inventory.setCategory(request.getCategory());
        inventory.setChickenPart(request.getChickenPart());
        inventory.setQuantity(request.getQuantity());
        inventory.setReorderLevel(request.getReorderLevel());
        inventory.setUnitPrice(request.getUnitPrice());
        inventory.setUnit("KG");
        inventory.setSupplier(request.getSupplier());
        inventory.setSupplierContact(request.getSupplierContact());
        inventory.setLastRestocked(LocalDateTime.now());
        inventory.setExpiryDate(request.getExpiryDate());
        inventory.setStorageLocation(request.getStorageLocation());
        inventory.setIsAvailable(request.getQuantity() > 0);
        
        Inventory savedItem = inventoryRepository.save(inventory);
        log.info("Inventory item added successfully: {} - {}", 
                savedItem.getItemCode(), savedItem.getItemName());
        
        return convertToDTO(savedItem);
    }
    
    public List<InventoryDTO> getAllInventory() {
        return inventoryRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public InventoryDTO getInventoryById(Long id) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory item not found with id: " + id));
        return convertToDTO(inventory);
    }
    
    public InventoryDTO getInventoryByCode(String itemCode) {
        Inventory inventory = inventoryRepository.findByItemCode(itemCode)
                .orElseThrow(() -> new RuntimeException("Inventory item not found with code: " + itemCode));
        return convertToDTO(inventory);
    }
    
    @Transactional
    public InventoryDTO updateInventoryItem(Long id, InventoryDTO.InventoryRequest request) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory item not found with id: " + id));
        
        // Check if item code is being changed and already exists
        if (!inventory.getItemCode().equals(request.getItemCode()) && 
            inventoryRepository.findByItemCode(request.getItemCode()).isPresent()) {
            throw new RuntimeException("Item code already exists: " + request.getItemCode());
        }
        
        inventory.setItemCode(request.getItemCode());
        inventory.setItemName(request.getItemName());
        inventory.setCategory(request.getCategory());
        inventory.setChickenPart(request.getChickenPart());
        inventory.setQuantity(request.getQuantity());
        inventory.setReorderLevel(request.getReorderLevel());
        inventory.setUnitPrice(request.getUnitPrice());
        inventory.setSupplier(request.getSupplier());
        inventory.setSupplierContact(request.getSupplierContact());
        inventory.setExpiryDate(request.getExpiryDate());
        inventory.setStorageLocation(request.getStorageLocation());
        inventory.setIsAvailable(request.getQuantity() > 0);
        
        Inventory updatedItem = inventoryRepository.save(inventory);
        log.info("Inventory item updated: {} - {}", 
                updatedItem.getItemCode(), updatedItem.getItemName());
        
        return convertToDTO(updatedItem);
    }
    
    @Transactional
    public void deleteInventoryItem(Long id) {
        if (!inventoryRepository.existsById(id)) {
            throw new RuntimeException("Inventory item not found with id: " + id);
        }
        inventoryRepository.deleteById(id);
        log.info("Inventory item deleted with id: {}", id);
    }
    
    @Transactional
    public InventoryDTO restockInventory(Long id, InventoryDTO.RestockRequest request) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory item not found with id: " + id));
        
        int newQuantity = inventory.getQuantity() + request.getQuantityToAdd();
        inventory.setQuantity(newQuantity);
        inventory.setLastRestocked(LocalDateTime.now());
        inventory.setIsAvailable(newQuantity > 0);
        
        // Update supplier info if provided
        if (request.getSupplier() != null) {
            inventory.setSupplier(request.getSupplier());
        }
        
        if (request.getExpiryDate() != null) {
            inventory.setExpiryDate(request.getExpiryDate());
        }
        
        Inventory restockedItem = inventoryRepository.save(inventory);
        log.info("Inventory restocked: {} - New quantity: {}", 
                restockedItem.getItemCode(), restockedItem.getQuantity());
        
        return convertToDTO(restockedItem);
    }
    
    @Transactional
    public InventoryDTO consumeInventory(String itemCode, Integer quantityToConsume) {
        Inventory inventory = inventoryRepository.findByItemCode(itemCode)
                .orElseThrow(() -> new RuntimeException("Inventory item not found with code: " + itemCode));
        
        if (inventory.getQuantity() < quantityToConsume) {
            throw new RuntimeException("Insufficient stock. Available: " + inventory.getQuantity() + 
                                     ", Requested: " + quantityToConsume);
        }
        
        int newQuantity = inventory.getQuantity() - quantityToConsume;
        inventory.setQuantity(newQuantity);
        inventory.setIsAvailable(newQuantity > 0);
        
        Inventory updatedItem = inventoryRepository.save(inventory);
        log.info("Inventory consumed: {} - Quantity reduced by: {}, New quantity: {}", 
                updatedItem.getItemCode(), quantityToConsume, updatedItem.getQuantity());
        
        return convertToDTO(updatedItem);
    }
    
    public List<InventoryDTO> getLowStockItems() {
        return inventoryRepository.findLowStockItems()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<InventoryDTO> getOutOfStockItems() {
        return inventoryRepository.findOutOfStockItems()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<InventoryDTO> getInventoryByCategory(String category) {
        return inventoryRepository.findByCategory(category)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<InventoryDTO> getInventoryByChickenPart(String chickenPart) {
        return inventoryRepository.findByChickenPart(chickenPart)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<InventoryDTO> getAvailableInventory() {
        return inventoryRepository.findByIsAvailable(true)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public Double getTotalInventoryValue() {
        Double value = inventoryRepository.getTotalInventoryValue();
        return value != null ? value : 0.0;
    }
    
    public List<InventoryDTO> getInventoryBySupplier(String supplier) {
        return inventoryRepository.findBySupplier(supplier)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    private InventoryDTO convertToDTO(Inventory inventory) {
        return new InventoryDTO(
                inventory.getId(),
                inventory.getItemCode(),
                inventory.getItemName(),
                inventory.getCategory(),
                inventory.getChickenPart(),
                inventory.getQuantity(),
                inventory.getReorderLevel(),
                inventory.getUnitPrice(),
                inventory.getUnit(),
                inventory.getSupplier(),
                inventory.getSupplierContact(),
                inventory.getLastRestocked(),
                inventory.getExpiryDate(),
                inventory.getStorageLocation(),
                inventory.getIsAvailable()
        );
    }
}