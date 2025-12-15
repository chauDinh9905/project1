package com.restaurant.ordersystem.repository;

import com.restaurant.ordersystem.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    
    // Lấy items theo order
    List<OrderItem> findByOrderId(Integer orderId);
    
    // Lấy items theo menu item (để thống kê món bán chạy)
    List<OrderItem> findByMenuItemId(Integer menuItemId);
}