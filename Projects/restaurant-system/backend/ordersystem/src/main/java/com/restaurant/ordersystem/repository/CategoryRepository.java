package com.restaurant.ordersystem.repository;

import com.restaurant.ordersystem.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    
    // Tìm category theo tên
    List<Category> findByNameContaining(String name);
    
    // Lấy tất cả, sắp xếp theo sort_order
    List<Category> findAllByOrderBySortOrder();
}