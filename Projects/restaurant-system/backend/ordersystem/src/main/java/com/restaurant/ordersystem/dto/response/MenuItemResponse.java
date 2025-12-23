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
public class MenuItemResponse {
    
    private Integer id;
    private String name;
    private String description;
    private BigDecimal price;
    private String image;
    private Boolean available;
    private Integer categoryId;
    private String categoryName;
}