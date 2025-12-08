package com.restaurant.ordersystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;  
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false, length = 100)
    private String name;
    
    @Column(name = "sort_order")
    private Integer sortOrder;
    
    @JsonIgnore 
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<MenuItem> menuItems;
}