package com.shophub.inventory_service.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryRequest {
    private Long id;
    private String skuCode;
    private Integer quantity;
}
