package com.countrychicken.controller;

import com.countrychicken.dto.CustomerDTO;
import com.countrychicken.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@Tag(name = "Customer Management", description = "APIs for managing customers")
public class CustomerController {
    
    private final CustomerService customerService;
    
    @PostMapping
    @Operation(summary = "Create a new customer")
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO.CreateCustomerRequest request) {
        CustomerDTO createdCustomer = customerService.createCustomer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
    }
    
    @GetMapping
    @Operation(summary = "Get all customers")
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<CustomerDTO> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get customer by ID")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        CustomerDTO customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }
    
    @GetMapping("/code/{customerCode}")
    @Operation(summary = "Get customer by customer code")
    public ResponseEntity<CustomerDTO> getCustomerByCode(@PathVariable String customerCode) {
        CustomerDTO customer = customerService.getCustomerByCode(customerCode);
        return ResponseEntity.ok(customer);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update customer")
    public ResponseEntity<CustomerDTO> updateCustomer(
            @PathVariable Long id,
            @RequestBody CustomerDTO.UpdateCustomerRequest request) {
        CustomerDTO updatedCustomer = customerService.updateCustomer(id, request);
        return ResponseEntity.ok(updatedCustomer);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a customer")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/active")
    @Operation(summary = "Get all active customers")
    public ResponseEntity<List<CustomerDTO>> getActiveCustomers() {
        List<CustomerDTO> customers = customerService.getActiveCustomers();
        return ResponseEntity.ok(customers);
    }
    
    @GetMapping("/type/{customerType}")
    @Operation(summary = "Get customers by type")
    public ResponseEntity<List<CustomerDTO>> getCustomersByType(@PathVariable String customerType) {
        List<CustomerDTO> customers = customerService.getCustomersByType(customerType);
        return ResponseEntity.ok(customers);
    }
    
    @GetMapping("/search/name/{name}")
    @Operation(summary = "Search customers by name")
    public ResponseEntity<List<CustomerDTO>> searchCustomersByName(@PathVariable String name) {
        List<CustomerDTO> customers = customerService.searchCustomersByName(name);
        return ResponseEntity.ok(customers);
    }
    
    @GetMapping("/top/{minOrders}")
    @Operation(summary = "Get top customers with minimum orders")
    public ResponseEntity<List<CustomerDTO>> getTopCustomers(@PathVariable Integer minOrders) {
        List<CustomerDTO> customers = customerService.getTopCustomers(minOrders);
        return ResponseEntity.ok(customers);
    }
}