package com.shophub.product_service.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(value = "product")
@AllArgsConstructor
@NoArgsConstructor
@Builder //generate a builder pattern implementation for a class.
/*
* Product product = Product.builder()
                      .name("Product #1")
                      .description("This is the 'Product #1' description")
                      .price(87.99)
                      .build();
* */
@Data
public class Product {
    @Id
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
}
