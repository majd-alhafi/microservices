package com.shophub.inventory_service.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryResponse {
    private Long id;
    private String skuCode;
    private Integer quantity;
}
