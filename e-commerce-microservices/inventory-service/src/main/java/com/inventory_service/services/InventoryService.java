package com.inventory_service.services;

import com.inventory_service.dto.InventoryResponse;
import com.inventory_service.models.Inventory;
import com.inventory_service.repos.InventoryRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepo inventoryRepo;

    @Transactional
    public List<InventoryResponse> isInStock(List<String> skuCode){

        List<Inventory> inventorys = inventoryRepo.findBySkuCodeIn(skuCode);

        return inventorys.stream().map(this::mapToDto).toList();
    }

    private InventoryResponse mapToDto(Inventory inventory) {
//        boolean instock = false;
//         if (inventory.getQuantity()>0){
//             instock = true;
//         }else {
//             throw  new IllegalArgumentException("The Item  "+ inventory.getSkuCode() + "is not exist");
//         }

        return InventoryResponse.builder()
                .isInStock(
                        inventory.getQuantity()>0
                )
                .skuCode(inventory.getSkuCode())
                .build();
    }

}
