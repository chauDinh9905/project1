package com.restaurant.ordersystem.service;

import com.restaurant.ordersystem.dto.response.CategoryResponse;
import com.restaurant.ordersystem.dto.response.MenuItemResponse;
import com.restaurant.ordersystem.model.MenuItem;
import com.restaurant.ordersystem.repository.CategoryRepository;
import com.restaurant.ordersystem.repository.MenuItemRepository;
import com.restaurant.ordersystem.util.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {
    
    private final MenuItemRepository menuItemRepository;
    private final CategoryRepository categoryRepository;
    private final DtoMapper mapper;
    
    
    // Lấy tất cả món ăn
    
    public List<MenuItemResponse> getAllMenuItems() {
        return menuItemRepository.findAll().stream()
                .map(mapper::toMenuItemResponse)
                .collect(Collectors.toList());
    }

    // Lấy chi tiết 1 món ăn theo ID
    public MenuItemResponse getMenuItemById(Integer id) {
       MenuItem menuItem = menuItemRepository.findById(id).orElseThrow(() -> new RuntimeException("Menu item not found with id: " + id));
       return mapper.toMenuItemResponse(menuItem);
    }
    
    
     //Lấy món ăn còn bán
     
    public List<MenuItemResponse> getAvailableMenuItems() {
        return menuItemRepository.findByAvailableTrue().stream()
                .map(mapper::toMenuItemResponse)
                .collect(Collectors.toList());
    }
    
    
    // Lấy món theo category
    
    public List<MenuItemResponse> getMenuItemsByCategory(Integer categoryId) {
        return menuItemRepository.findByCategoryIdAndAvailableTrue(categoryId).stream()
                .map(mapper::toMenuItemResponse)
                .collect(Collectors.toList());
    }
    
    
     //Lấy tất cả categories
     
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAllByOrderBySortOrder().stream()
                .map(mapper::toCategoryResponse)
                .collect(Collectors.toList());
    }
}