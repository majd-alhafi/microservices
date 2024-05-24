package com.shophub.inventory_service.repositories;

import com.shophub.inventory_service.models.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory,Long> {
    Optional<Inventory> findBySkuCode(String skuCode);
    @Query("SELECT i FROM Inventory i WHERE i.skuCode IN :skuCodeList")
    List<Inventory> findAllBySkuCodeIn(@Param("skuCodeList") List<String> skuCodeList);
}
