package com.restaurant.ordersystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponse {
    
    private Integer id;
    private Integer menuItemId;
    private String menuItemName;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal subtotal; // quantity * price
    private String notes;
}