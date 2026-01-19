package com.countrychicken.repository;

import com.countrychicken.entity.Inventory;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    
    Optional<Inventory> findByItemCode(String itemCode);
    
    List<Inventory> findByCategory(String category);
    
    List<Inventory> findByChickenPart(String chickenPart);
    
    List<Inventory> findByIsAvailable(Boolean isAvailable);
    
    @Query("SELECT i FROM Inventory i WHERE i.quantity <= i.reorderLevel")
    List<Inventory> findLowStockItems();
    
    @Query("SELECT i FROM Inventory i WHERE i.quantity = 0")
    List<Inventory> findOutOfStockItems();
    
    @Query("SELECT SUM(i.quantity * i.unitPrice) FROM Inventory i")
    Double getTotalInventoryValue();
    
    List<Inventory> findBySupplier(String supplier);
}