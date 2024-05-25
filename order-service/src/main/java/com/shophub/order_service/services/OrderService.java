package com.shophub.order_service.services;

import com.shophub.order_service.dto.InventoryResponse;
import com.shophub.order_service.dto.OrderLineItemDto;
import com.shophub.order_service.dto.OrderRequest;
import com.shophub.order_service.events.OrderPlacedEvent;
import com.shophub.order_service.models.Order;
import com.shophub.order_service.models.OrderLineItem;
import com.shophub.order_service.repositories.OrderRepository;
import lombok.*;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final DiscoveryClient discoveryClient;
    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    private final KafkaTemplate<String,OrderPlacedEvent> kafkaTemplate;
    public String placeOrder(OrderRequest orderRequest){
        List<OrderLineItem> orderLineItemList = orderRequest.getOrderLineItemDtoList()
                .stream()
                .map(this::mapToOrderLineItem).toList();
        Order order = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .orderLineItemList(orderLineItemList)
                .build();
        List<String> skuCodesList = orderRequest.getOrderLineItemDtoList().stream().map(OrderLineItemDto::getSkuCode).toList();
        // call Inventory service to place the order the product is in the stock
        Optional<String> inventoryServiceUrl = discoveryClient.getInstances("inventory-service")
                .stream()
                .findFirst()
                .map(si -> si.getUri().toString());
        InventoryResponse[] inventoryResponseArray;
        if (inventoryServiceUrl.isPresent()) {
            String uri = inventoryServiceUrl.get() + "/api/inventory/items";

            inventoryResponseArray = webClientBuilder.build().get()
                    .uri(uri,uriBuilder -> uriBuilder
                            .queryParam("sku-code-list", skuCodesList)
                            .build())
                    .retrieve()
                    .bodyToMono(InventoryResponse[].class)
                    .block(); // to make the request synchronous
        } else {
            throw new RuntimeException("inventory-service not found");
        }
        boolean allProductsInStock = Arrays.stream(inventoryResponseArray)
                .allMatch(InventoryResponse::isInStock); //check if all products is in the stock.
        if(allProductsInStock) {
            orderRepository.save(order);
            OrderPlacedEvent orderPlacedEvent = OrderPlacedEvent.builder()
                    .orderNumber(order.getOrderNumber())
                    .build();
            kafkaTemplate.send("NotificationTopic",orderPlacedEvent);
            return "successfully";
        }
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
