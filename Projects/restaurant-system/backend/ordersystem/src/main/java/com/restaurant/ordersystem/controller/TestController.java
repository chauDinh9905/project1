package com.restaurant.ordersystem.controller;

import com.restaurant.ordersystem.model.Category;
import com.restaurant.ordersystem.model.MenuItem;
import com.restaurant.ordersystem.model.RestaurantTable;
import com.restaurant.ordersystem.repository.CategoryRepository;
import com.restaurant.ordersystem.repository.MenuItemRepository;
import com.restaurant.ordersystem.repository.TableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TestController {
    
    private final CategoryRepository categoryRepository;
    private final MenuItemRepository menuItemRepository;
    private final TableRepository tableRepository;
    
    @GetMapping("/categories")
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }
    
    @GetMapping("/menu")
    public List<MenuItem> getMenu() {
        return menuItemRepository.findAll();
    }
    
    @GetMapping("/tables")
    public List<RestaurantTable> getTables() {
        return tableRepository.findAll();
    }
}