package com.countrychicken.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "chicken_orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String orderNumber;
    
    @Column(nullable = false)
    private String customerName;
    
    @Column(nullable = false)
    private String customerPhone;
    
    @Column(nullable = false)
    private String chickenType; // FRESH, FROZEN, ORGANIC
    
    @Column(nullable = false)
    private String chickenPart; // WHOLE, LEGS, BREAST, WINGS
    
    @Column(nullable = false)
    private Integer quantity; // in kilograms
    
    @Column(nullable = false)
    private Double pricePerKg;
    
    @Column(nullable = false)
    private Double totalPrice;
    
    @Column(nullable = false)
    private String status; // PENDING, CONFIRMED, PROCESSING, DELIVERED, CANCELLED
    
    @Column(nullable = false)
    private LocalDateTime orderDate;
    
    private LocalDateTime deliveryDate;
    
    @Column(nullable = false)
    private String deliveryAddress;
    
    private String specialInstructions;
    
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    private LocalDateTime updatedAt;
    
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}