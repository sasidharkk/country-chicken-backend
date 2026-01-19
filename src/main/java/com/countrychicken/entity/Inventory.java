package com.countrychicken.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventory")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String itemCode;
    
    @Column(nullable = false)
    private String itemName;
    
    @Column(nullable = false)
    private String category; // FRESH, FROZEN, PROCESSED
    
    @Column(nullable = false)
    private String chickenPart; // WHOLE, LEGS, BREAST, WINGS, THIGHS
    
    @Column(nullable = false)
    private Integer quantity; // in kilograms
    
    @Column(nullable = false)
    private Integer reorderLevel;
    
    @Column(nullable = false)
    private Double unitPrice;
    
    @Column(nullable = false)
    private String unit; // KG
    
    private String supplier;
    
    private String supplierContact;
    
    @Column(nullable = false)
    private LocalDateTime lastRestocked = LocalDateTime.now();
    
    @Column(nullable = false)
    private LocalDateTime expiryDate;
    
    @Column(nullable = false)
    private String storageLocation; // FREEZER_A, FREEZER_B, FRESH_SECTION
    
    @Column(nullable = false)
    private Boolean isAvailable = true;
    
    @PreUpdate
    protected void onUpdate() {
        if (this.quantity <= this.reorderLevel) {
            // Log low stock warning
            System.out.println("Warning: Low stock for item " + this.itemCode);
        }
    }
}