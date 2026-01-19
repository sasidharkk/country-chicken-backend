package com.countrychicken.service;

import com.countrychicken.dto.CustomerDTO;
import com.countrychicken.entity.Customer;
import com.countrychicken.repository.CustomerRepository;
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
public class CustomerService {
    
    private final CustomerRepository customerRepository;
    
    @Transactional
    public CustomerDTO createCustomer(CustomerDTO.CreateCustomerRequest request) {
        log.info("Creating new customer: {}", request.getName());
        
        // Check if email or phone already exists
        if (customerRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered: " + request.getEmail());
        }
        
        if (customerRepository.existsByPhone(request.getPhone())) {
            throw new RuntimeException("Phone number already registered: " + request.getPhone());
        }
        
        Customer customer = new Customer();
        customer.setCustomerCode(generateCustomerCode());
        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());
        customer.setAddress(request.getAddress());
        customer.setCity(request.getCity());
        customer.setState(request.getState());
        customer.setZipCode(request.getZipCode());
        customer.setCustomerType(request.getCustomerType() != null ? request.getCustomerType() : "REGULAR");
        customer.setRegistrationDate(LocalDateTime.now());
        customer.setTotalOrders(0);
        customer.setTotalSpent(0.0);
        customer.setIsActive(true);
        
        Customer savedCustomer = customerRepository.save(customer);
        log.info("Customer created successfully: {} - {}", 
                savedCustomer.getCustomerCode(), savedCustomer.getName());
        
        return convertToDTO(savedCustomer);
    }
    
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public CustomerDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
        return convertToDTO(customer);
    }
    
    public CustomerDTO getCustomerByCode(String customerCode) {
        Customer customer = customerRepository.findByCustomerCode(customerCode)
                .orElseThrow(() -> new RuntimeException("Customer not found with code: " + customerCode));
        return convertToDTO(customer);
    }
    
    @Transactional
    public CustomerDTO updateCustomer(Long id, CustomerDTO.UpdateCustomerRequest request) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
        
        // Check if email is being changed and already exists
        if (!customer.getEmail().equals(request.getEmail()) && 
            customerRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered: " + request.getEmail());
        }
        
        // Check if phone is being changed and already exists
        if (!customer.getPhone().equals(request.getPhone()) && 
            customerRepository.existsByPhone(request.getPhone())) {
            throw new RuntimeException("Phone number already registered: " + request.getPhone());
        }
        
        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());
        customer.setAddress(request.getAddress());
        customer.setCity(request.getCity());
        customer.setState(request.getState());
        customer.setZipCode(request.getZipCode());
        customer.setCustomerType(request.getCustomerType());
        customer.setIsActive(request.getIsActive());
        
        Customer updatedCustomer = customerRepository.save(customer);
        log.info("Customer updated: {} - {}", 
                updatedCustomer.getCustomerCode(), updatedCustomer.getName());
        
        return convertToDTO(updatedCustomer);
    }
    
    @Transactional
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new RuntimeException("Customer not found with id: " + id);
        }
        customerRepository.deleteById(id);
        log.info("Customer deleted with id: {}", id);
    }
    
    public List<CustomerDTO> getActiveCustomers() {
        return customerRepository.findByIsActive(true)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<CustomerDTO> getCustomersByType(String customerType) {
        return customerRepository.findByCustomerType(customerType)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<CustomerDTO> searchCustomersByName(String name) {
        return customerRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public void updateCustomerStats(String customerPhone, Double orderAmount) {
        Customer customer = customerRepository.findByPhone(customerPhone)
                .orElseThrow(() -> new RuntimeException("Customer not found with phone: " + customerPhone));
        
        customer.setTotalOrders(customer.getTotalOrders() + 1);
        customer.setTotalSpent(customer.getTotalSpent() + orderAmount);
        
        customerRepository.save(customer);
        log.debug("Updated stats for customer: {} - Total Orders: {}, Total Spent: {}", 
                customer.getName(), customer.getTotalOrders(), customer.getTotalSpent());
    }
    
    public List<CustomerDTO> getTopCustomers(Integer minOrders) {
        return customerRepository.findTopCustomers(minOrders)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    private String generateCustomerCode() {
        return "CUST-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }
    
    private CustomerDTO convertToDTO(Customer customer) {
        return new CustomerDTO(
                customer.getId(),
                customer.getCustomerCode(),
                customer.getName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getAddress(),
                customer.getCity(),
                customer.getState(),
                customer.getZipCode(),
                customer.getRegistrationDate(),
                customer.getTotalOrders(),
                customer.getTotalSpent(),
                customer.getIsActive(),
                customer.getCustomerType()
        );
    }
}