package com.restaurant.ordersystem.repository;

import com.restaurant.ordersystem.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    
    // Tìm đơn theo order number
    Optional<Order> findByOrderNumber(String orderNumber);
    
    // Lấy đơn theo status
    List<Order> findByStatus(String status);
    
    // Lấy đơn theo nhiều status (pending, cooking, ready)
    List<Order> findByStatusIn(List<String> statuses);
    
    // Lấy đơn theo bàn
    List<Order> findByTableIdOrderByCreatedAtDesc(Integer tableId);
    
    // Lấy đơn trong khoảng thời gian
    List<Order> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
    
    // Custom query: Lấy đơn đang hoạt động
    @Query("SELECT o FROM Order o WHERE o.status IN ('pending', 'cooking', 'ready') ORDER BY o.createdAt DESC")
    List<Order> findActiveOrders();
}