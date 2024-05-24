package com.shophub.order_service.services;

import com.shophub.order_service.dto.InventoryResponse;
import com.shophub.order_service.dto.OrderLineItemDto;
import com.shophub.order_service.dto.OrderRequest;
import com.shophub.order_service.models.Order;
import com.shophub.order_service.models.OrderLineItem;
import com.shophub.order_service.repositories.OrderRepository;
import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    public void placeOrder(OrderRequest orderRequest){
        List<OrderLineItem> orderLineItemList = orderRequest.getOrderLineItemDtoList()
                .stream()
                .map(this::mapToOrderLineItem).toList();
        Order order = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .orderLineItemList(orderLineItemList)
                .build();
        List<String> skuCodesList = orderRequest.getOrderLineItemDtoList().stream().map(OrderLineItemDto::getSkuCode).toList();
        // call Inventory service to place the order the product is in the stock
        InventoryResponse[] inventoryResponseArray = webClientBuilder.build().get()
                .uri("http://localhost:8082/api/inventory/items",
                        uriBuilder -> uriBuilder.queryParam("sku-code-list",skuCodesList).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();//to make the request sync.
        boolean allProductsInStock = Arrays.stream(inventoryResponseArray)
                .allMatch(InventoryResponse::isInStock); //check if all products is in the stock.
        if(allProductsInStock)
            orderRepository.save(order);
        else{
            throw new IllegalArgumentException("Product is not in the stock");
        }
    }

    private OrderLineItem mapToOrderLineItem(OrderLineItemDto orderLineItemDto) {
        return  OrderLineItem.builder()
                .price(orderLineItemDto.getPrice())
                .quantity(orderLineItemDto.getQuantity())
                .skuCode(orderLineItemDto.getSkuCode())
                .build();
    }
}
