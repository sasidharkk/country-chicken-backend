package com.countrychicken.controller;

import com.countrychicken.dto.OrderDTO;
import com.countrychicken.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {
    
    private MockMvc mockMvc;
    
    @Mock
    private OrderService orderService;
    
    @InjectMocks
    private OrderController orderController;
    
    private ObjectMapper objectMapper;
    private OrderDTO orderDTO;
    private OrderDTO.CreateOrderRequest createRequest;
    
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
        objectMapper = new ObjectMapper();
        
        orderDTO = new OrderDTO(
                1L,
                "ORD-ABC123",
                "John Doe",
                "1234567890",
                "FRESH",
                "WHOLE",
                10,
                200.0,
                2000.0,
                "PENDING",
                LocalDateTime.now(),
                null,
                "123 Main St, City",
                "Please deliver before 5 PM",
                LocalDateTime.now()
        );
        
        createRequest = new OrderDTO.CreateOrderRequest(
                "John Doe",
                "1234567890",
                "FRESH",
                "WHOLE",
                10,
                200.0,
                "123 Main St, City",
                "Please deliver before 5 PM"
        );
    }
    
    @Test
    void testCreateOrder() throws Exception {
        when(orderService.createOrder(any(OrderDTO.CreateOrderRequest.class)))
                .thenReturn(orderDTO);
        
        mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.orderNumber").value("ORD-ABC123"))
                .andExpect(jsonPath("$.customerName").value("John Doe"))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }
    
    @Test
    void testGetAllOrders() throws Exception {
        List<OrderDTO> orders = Arrays.asList(orderDTO);
        when(orderService.getAllOrders()).thenReturn(orders);
        
        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].orderNumber").value("ORD-ABC123"));
    }
    
    @Test
    void testGetOrderById() throws Exception {
        when(orderService.getOrderById(1L)).thenReturn(orderDTO);
        
        mockMvc.perform(get("/api/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.orderNumber").value("ORD-ABC123"));
    }
    
    @Test
    void testGetOrdersByStatus() throws Exception {
        List<OrderDTO> orders = Arrays.asList(orderDTO);
        when(orderService.getOrdersByStatus("PENDING")).thenReturn(orders);
        
        mockMvc.perform(get("/api/orders/status/PENDING"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("PENDING"));
    }
    
    @Test
    void testDeleteOrder() throws Exception {
        mockMvc.perform(delete("/api/orders/1"))
                .andExpect(status().isNoContent());
    }
}