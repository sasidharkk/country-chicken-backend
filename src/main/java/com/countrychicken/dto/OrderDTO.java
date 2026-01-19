package com.countrychicken.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    
    private Long id;
    private String orderNumber;
    private String customerName;
    private String customerPhone;
    private String chickenType;
    private String chickenPart;
    private Integer quantity;
    private Double pricePerKg;
    private Double totalPrice;
    private String status;
    private LocalDateTime orderDate;
    private LocalDateTime deliveryDate;
    private String deliveryAddress;
    private String specialInstructions;
    private LocalDateTime createdAt;
    
    // For creating new order
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateOrderRequest {
        private String customerName;
        private String customerPhone;
        private String chickenType;
        private String chickenPart;
        private Integer quantity;
        private Double pricePerKg;
        private String deliveryAddress;
        private String specialInstructions;
    }
    
    // For updating order status
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateStatusRequest {
        private String status;
        private LocalDateTime deliveryDate;
    }
}