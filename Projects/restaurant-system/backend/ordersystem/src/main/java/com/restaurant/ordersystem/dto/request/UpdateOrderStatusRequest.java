package com.restaurant.ordersystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderStatusRequest {
    
    @NotBlank(message = "Status is required")
    private String status;
    // Valid values: pending, cooking, ready, completed, cancelled
}