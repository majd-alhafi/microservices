package com.shophub.inventory_service.controllers;

import com.shophub.inventory_service.dto.InventoryRequest;
import com.shophub.inventory_service.dto.InventoryResponse;
import com.shophub.inventory_service.models.Inventory;
import com.shophub.inventory_service.services.InventoryService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;
    @GetMapping("/{sku-code}")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@PathVariable("sku-code") String skuCode) {
        return inventoryService.isInStock(skuCode);
    }
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> getAllInventory() {
        return inventoryService.getAllInventory();
    }
    @GetMapping("/items")
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> getInventoriesByCode(@RequestParam("sku-code-list") List<String> skuCodeList){
        return inventoryService.getInventoriesByCode(skuCodeList);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createInventory(@RequestBody InventoryRequest inventoryRequest){
        inventoryService.createInventory(inventoryRequest);
        return "Successfully";
    }
}
