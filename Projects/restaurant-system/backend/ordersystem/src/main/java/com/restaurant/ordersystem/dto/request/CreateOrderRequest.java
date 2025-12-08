package com.restaurant.ordersystem.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {
    
    @NotNull(message = "Table ID is required")
    private Integer tableId;
    
    @NotEmpty(message = "Order must have at least one item")
    @Valid
    private List<OrderItemRequest> items;
    
    private String notes;
}