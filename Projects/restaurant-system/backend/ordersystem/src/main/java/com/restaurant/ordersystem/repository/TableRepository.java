package com.restaurant.ordersystem.repository;

import com.restaurant.ordersystem.model.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TableRepository extends JpaRepository<RestaurantTable, Integer> {
    
    // Tìm bàn theo số bàn
    Optional<RestaurantTable> findByTableNumber(Integer tableNumber);
    
    // Lấy bàn trống
    List<RestaurantTable> findByStatus(String status);
    
    // Lấy bàn theo capacity
    List<RestaurantTable> findByCapacityGreaterThanEqual(Integer capacity);
}