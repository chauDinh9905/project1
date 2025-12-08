package com.restaurant.ordersystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tables")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantTable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "table_number", nullable = false, unique = true)
    private Integer tableNumber;
    
    @Column(nullable = false)
    private Integer capacity = 4;
    
    @Column(length = 20, nullable = false)
    private String status = "available";
    // Status values: "available", "occupied"
}