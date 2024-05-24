package com.shophub.order_service.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "t_order_line_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderLineItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;

}
