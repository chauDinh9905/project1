package com.restaurant.ordersystem.controller;

import com.restaurant.ordersystem.dto.response.CategoryResponse;
import com.restaurant.ordersystem.dto.response.MenuItemResponse;
import com.restaurant.ordersystem.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MenuController {
    
    private final MenuService menuService;
    
    /*
      Lấy tất cả món ăn
      GET /api/menu
     */
    @GetMapping
    public ResponseEntity<List<MenuItemResponse>> getAllMenuItems() {
        List<MenuItemResponse> items = menuService.getAllMenuItems();
        return ResponseEntity.ok(items);
    }

    /*
  Lấy chi tiết 1 món ăn theo ID
  GET /api/menu/{id}
 */
   @GetMapping("/{id}")
    public ResponseEntity<MenuItemResponse> getMenuItemById(@PathVariable Integer id) {
    MenuItemResponse item = menuService.getMenuItemById(id);
    return ResponseEntity.ok(item);
    }
    
    /*
      Lấy món ăn còn bán
      GET /api/menu/available
     */
    @GetMapping("/available")
    public ResponseEntity<List<MenuItemResponse>> getAvailableMenuItems() {
        List<MenuItemResponse> items = menuService.getAvailableMenuItems();
        return ResponseEntity.ok(items);
    }
    
    /*
      Lấy món theo category
      GET /api/menu/category/{categoryId}
     */
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<MenuItemResponse>> getMenuItemsByCategory(@PathVariable Integer categoryId) {
        List<MenuItemResponse> items = menuService.getMenuItemsByCategory(categoryId);
        return ResponseEntity.ok(items);
    }
    
    /*
      Lấy tất cả categories
      GET /api/categories
     */
    @GetMapping("/categories")
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<CategoryResponse> categories = menuService.getAllCategories();
        return ResponseEntity.ok(categories);
    }
}