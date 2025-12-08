package com.restaurant.ordersystem.repository;

import com.restaurant.ordersystem.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Integer> {
    
    // Lấy món theo category
    List<MenuItem> findByCategoryId(Integer categoryId);
    
    // Lấy món còn bán
    List<MenuItem> findByAvailableTrue();
    
    // Lấy món theo category và còn bán
    List<MenuItem> findByCategoryIdAndAvailableTrue(Integer categoryId);
    
    // Tìm món theo tên
    List<MenuItem> findByNameContaining(String name);
}