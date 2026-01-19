package com.countrychicken.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDTO {
    
    private Long id;
    private String itemCode;
    private String itemName;
    private String category;
    private String chickenPart;
    private Integer quantity;
    private Integer reorderLevel;
    private Double unitPrice;
    private String unit;
    private String supplier;
    private String supplierContact;
    private LocalDateTime lastRestocked;
    private LocalDateTime expiryDate;
    private String storageLocation;
    private Boolean isAvailable;
    
    // For creating/updating inventory
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InventoryRequest {
        private String itemCode;
        private String itemName;
        private String category;
        private String chickenPart;
        private Integer quantity;
        private Integer reorderLevel;
        private Double unitPrice;
        private String supplier;
        private String supplierContact;
        private LocalDateTime expiryDate;
        private String storageLocation;
    }
    
    // For restocking inventory
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RestockRequest {
        private Integer quantityToAdd;
        private String supplier;
        private LocalDateTime expiryDate;
    }
}