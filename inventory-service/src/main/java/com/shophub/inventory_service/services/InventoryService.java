package com.shophub.inventory_service.services;

import com.shophub.inventory_service.dto.InventoryRequest;
import com.shophub.inventory_service.dto.InventoryResponse;
import com.shophub.inventory_service.models.Inventory;
import com.shophub.inventory_service.repositories.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    @Transactional(readOnly = true)
    public boolean isInStock(String skuCode) {
        return inventoryRepository.findBySkuCode(skuCode).isPresent();
    }

    public void createInventory(InventoryRequest inventoryRequest) {
        Inventory inventory =  Inventory.builder()
                .skuCode(inventoryRequest.getSkuCode())
                .quantity(inventoryRequest.getQuantity())
                .build();
        inventoryRepository.save(inventory);
    }

    public List<InventoryResponse> getInventoriesByCode(List<String> skuCodeList) {
        return inventoryRepository.findAllBySkuCodeIn(skuCodeList).stream().map(this::mapToInventoryResponse).toList();
    }

    private InventoryResponse mapToInventoryResponse(Inventory inventory) {
        return InventoryResponse.builder()
                .id(inventory.getId())
                .quantity(inventory.getQuantity())
                .skuCode(inventory.getSkuCode())
                .build();
    }

    public List<InventoryResponse> getAllInventory() {
        return inventoryRepository.findAll().stream().map(this::mapToInventoryResponse).toList();
    }
}
