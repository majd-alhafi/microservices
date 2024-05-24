package com.shophub.order_service.dto;

import com.shophub.order_service.models.OrderLineItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private List<OrderLineItemDto> orderLineItemDtoList;
}
