package com.inventory_service.repos;

import com.inventory_service.models.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepo extends JpaRepository<Inventory,Long> {
    Optional<Inventory> findBySkuCode(String skuCode);
    List<Inventory> findBySkuCodeIn(List<String> skuCode);
}
