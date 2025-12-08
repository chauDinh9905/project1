package com.restaurant.ordersystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableResponse {
    
    private Integer id;
    private Integer tableNumber;
    private Integer capacity;
    private String status;
}