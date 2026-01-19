package com.countrychicken.controller;

import com.countrychicken.dto.OrderDTO;
import com.countrychicken.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Order Management", description = "APIs for managing chicken orders")
public class OrderController {
    
    private final OrderService orderService;
    
    @PostMapping
    @Operation(summary = "Create a new order")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO.CreateOrderRequest request) {
        OrderDTO createdOrder = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }
    
    @GetMapping
    @Operation(summary = "Get all orders")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get order by ID")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
        OrderDTO order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }
    
    @GetMapping("/number/{orderNumber}")
    @Operation(summary = "Get order by order number")
    public ResponseEntity<OrderDTO> getOrderByNumber(@PathVariable String orderNumber) {
        OrderDTO order = orderService.getOrderByOrderNumber(orderNumber);
        return ResponseEntity.ok(order);
    }
    
    @PutMapping("/{id}/status")
    @Operation(summary = "Update order status")
    public ResponseEntity<OrderDTO> updateOrderStatus(
            @PathVariable Long id,
            @RequestBody OrderDTO.UpdateStatusRequest request) {
        OrderDTO updatedOrder = orderService.updateOrderStatus(id, request);
        return ResponseEntity.ok(updatedOrder);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an order")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/status/{status}")
    @Operation(summary = "Get orders by status")
    public ResponseEntity<List<OrderDTO>> getOrdersByStatus(@PathVariable String status) {
        List<OrderDTO> orders = orderService.getOrdersByStatus(status);
        return ResponseEntity.ok(orders);
    }
    
    @GetMapping("/customer/name/{customerName}")
    @Operation(summary = "Get orders by customer name")
    public ResponseEntity<List<OrderDTO>> getOrdersByCustomerName(@PathVariable String customerName) {
        List<OrderDTO> orders = orderService.getOrdersByCustomerName(customerName);
        return ResponseEntity.ok(orders);
    }
    
    @GetMapping("/customer/phone/{phone}")
    @Operation(summary = "Get orders by customer phone")
    public ResponseEntity<List<OrderDTO>> getOrdersByCustomerPhone(@PathVariable String phone) {
        List<OrderDTO> orders = orderService.getOrdersByCustomerPhone(phone);
        return ResponseEntity.ok(orders);
    }
    
    @GetMapping("/revenue/total")
    @Operation(summary = "Get total revenue")
    public ResponseEntity<Double> getTotalRevenue() {
        Double revenue = orderService.getTotalRevenue();
        return ResponseEntity.ok(revenue);
    }
    
    @GetMapping("/stats/count/{status}")
    @Operation(summary = "Get order count by status")
    public ResponseEntity<Long> getOrderCountByStatus(@PathVariable String status) {
        Long count = orderService.getOrderCountByStatus(status);
        return ResponseEntity.ok(count);
    }
    
    @GetMapping("/amount/above/{minAmount}")
    @Operation(summary = "Get orders above specified amount")
    public ResponseEntity<List<OrderDTO>> getOrdersAboveAmount(@PathVariable Double minAmount) {
        List<OrderDTO> orders = orderService.getOrdersAboveAmount(minAmount);
        return ResponseEntity.ok(orders);
    }
}