package com.countrychicken.service;

import com.countrychicken.dto.OrderDTO;
import com.countrychicken.entity.Order;
import com.countrychicken.repository.OrderRepository;
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
public class OrderService {
    
    private final OrderRepository orderRepository;
    
    @Transactional
    public OrderDTO createOrder(OrderDTO.CreateOrderRequest request) {
        log.info("Creating new order for customer: {}", request.getCustomerName());
        
        Order order = new Order();
        order.setOrderNumber(generateOrderNumber());
        order.setCustomerName(request.getCustomerName());
        order.setCustomerPhone(request.getCustomerPhone());
        order.setChickenType(request.getChickenType());
        order.setChickenPart(request.getChickenPart());
        order.setQuantity(request.getQuantity());
        order.setPricePerKg(request.getPricePerKg());
        order.setTotalPrice(request.getQuantity() * request.getPricePerKg());
        order.setStatus("PENDING");
        order.setOrderDate(LocalDateTime.now());
        order.setDeliveryAddress(request.getDeliveryAddress());
        order.setSpecialInstructions(request.getSpecialInstructions());
        
        Order savedOrder = orderRepository.save(order);
        log.info("Order created successfully: {}", savedOrder.getOrderNumber());
        
        return convertToDTO(savedOrder);
    }
    
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        return convertToDTO(order);
    }
    
    public OrderDTO getOrderByOrderNumber(String orderNumber) {
        Order order = orderRepository.findByOrderNumber(orderNumber);
        if (order == null) {
            throw new RuntimeException("Order not found with number: " + orderNumber);
        }
        return convertToDTO(order);
    }
    
    @Transactional
    public OrderDTO updateOrderStatus(Long id, OrderDTO.UpdateStatusRequest request) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        
        order.setStatus(request.getStatus());
        if (request.getDeliveryDate() != null) {
            order.setDeliveryDate(request.getDeliveryDate());
        }
        order.setUpdatedAt(LocalDateTime.now());
        
        Order updatedOrder = orderRepository.save(order);
        log.info("Order status updated to {} for order: {}", 
                request.getStatus(), updatedOrder.getOrderNumber());
        
        return convertToDTO(updatedOrder);
    }
    
    @Transactional
    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new RuntimeException("Order not found with id: " + id);
        }
        orderRepository.deleteById(id);
        log.info("Order deleted with id: {}", id);
    }
    
    public List<OrderDTO> getOrdersByStatus(String status) {
        return orderRepository.findByStatus(status)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<OrderDTO> getOrdersByCustomerName(String customerName) {
        return orderRepository.findByCustomerNameContainingIgnoreCase(customerName)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<OrderDTO> getOrdersByCustomerPhone(String phone) {
        return orderRepository.findByCustomerPhone(phone)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public Double getTotalRevenue() {
        Double revenue = orderRepository.getTotalRevenue();
        return revenue != null ? revenue : 0.0;
    }
    
    public Long getOrderCountByStatus(String status) {
        return orderRepository.countByStatus(status);
    }
    
    public List<OrderDTO> getOrdersAboveAmount(Double minAmount) {
        return orderRepository.findOrdersAboveAmount(minAmount)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    private String generateOrderNumber() {
        return "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    
    private OrderDTO convertToDTO(Order order) {
        return new OrderDTO(
                order.getId(),
                order.getOrderNumber(),
                order.getCustomerName(),
                order.getCustomerPhone(),
                order.getChickenType(),
                order.getChickenPart(),
                order.getQuantity(),
                order.getPricePerKg(),
                order.getTotalPrice(),
                order.getStatus(),
                order.getOrderDate(),
                order.getDeliveryDate(),
                order.getDeliveryAddress(),
                order.getSpecialInstructions(),
                order.getCreatedAt()
        );
    }
}