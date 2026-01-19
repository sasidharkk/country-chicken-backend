package com.countrychicken.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    
    private Long id;
    private String customerCode;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private LocalDateTime registrationDate;
    private Integer totalOrders;
    private Double totalSpent;
    private Boolean isActive;
    private String customerType;
    
    // For creating new customer
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateCustomerRequest {
        private String name;
        private String email;
        private String phone;
        private String address;
        private String city;
        private String state;
        private String zipCode;
        private String customerType;
    }
    
    // For updating customer
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateCustomerRequest {
        private String name;
        private String email;
        private String phone;
        private String address;
        private String city;
        private String state;
        private String zipCode;
        private String customerType;
        private Boolean isActive;
    }
}