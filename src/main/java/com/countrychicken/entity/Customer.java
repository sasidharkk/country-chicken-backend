package com.countrychicken.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String customerCode;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String phone;
    
    private String address;
    
    private String city;
    
    private String state;
    
    private String zipCode;
    
    @Column(nullable = false)
    private LocalDateTime registrationDate = LocalDateTime.now();
    
    @Column(nullable = false)
    private Integer totalOrders = 0;
    
    @Column(nullable = false)
    private Double totalSpent = 0.0;
    
    @Column(nullable = false)
    private Boolean isActive = true;
    
    private String customerType; // REGULAR, WHOLESALE, RETAILER
    
    @PreUpdate
    protected void onUpdate() {
        if (this.totalOrders == null) this.totalOrders = 0;
        if (this.totalSpent == null) this.totalSpent = 0.0;
    }
}