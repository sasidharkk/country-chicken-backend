package com.countrychicken.repository;

import com.countrychicken.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    
    Optional<Customer> findByCustomerCode(String customerCode);
    
    Optional<Customer> findByEmail(String email);
    
    Optional<Customer> findByPhone(String phone);
    
    List<Customer> findByIsActive(Boolean isActive);
    
    List<Customer> findByCustomerType(String customerType);
    
    List<Customer> findByNameContainingIgnoreCase(String name);
    
    @Query("SELECT c FROM Customer c WHERE c.totalOrders >= :minOrders")
    List<Customer> findTopCustomers(@Param("minOrders") Integer minOrders);
    
    boolean existsByEmail(String email);
    
    boolean existsByPhone(String phone);
}