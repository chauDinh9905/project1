package com.restaurant.ordersystem.service;

import com.restaurant.ordersystem.dto.request.CreateOrderRequest;
import com.restaurant.ordersystem.dto.request.OrderItemRequest;
import com.restaurant.ordersystem.dto.response.OrderResponse;
import com.restaurant.ordersystem.model.*;
import com.restaurant.ordersystem.repository.*;
import com.restaurant.ordersystem.util.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final MenuItemRepository menuItemRepository;
    private final TableRepository tableRepository;
    private final DtoMapper mapper;
    
    
     // Tạo đơn hàng mới
     
    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request) {
        // 1. Tìm bàn
        RestaurantTable table = tableRepository.findById(request.getTableId())
                .orElseThrow(() -> new RuntimeException("Table not found with id: " + request.getTableId()));
        
        // 2. Tạo order
        Order order = new Order();
        order.setOrderNumber(generateOrderNumber());
        order.setTable(table);
        order.setStatus("pending");
        order.setNotes(request.getNotes());
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        
        // 3. Thêm order items
        BigDecimal totalAmount = BigDecimal.ZERO;
        
        for (OrderItemRequest itemReq : request.getItems()) {
            MenuItem menuItem = menuItemRepository.findById(itemReq.getMenuItemId())
                    .orElseThrow(() -> new RuntimeException("Menu item not found with id: " + itemReq.getMenuItemId()));
            
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setMenuItem(menuItem);
            orderItem.setQuantity(itemReq.getQuantity());
            orderItem.setPrice(menuItem.getPrice());
            orderItem.setNotes(itemReq.getNotes());
            
            order.addOrderItem(orderItem);
            
            // Tính tổng
            BigDecimal itemTotal = menuItem.getPrice()
                    .multiply(BigDecimal.valueOf(itemReq.getQuantity()));
            totalAmount = totalAmount.add(itemTotal);
        }
        
        order.setTotalAmount(totalAmount);
        
        // 4. Cập nhật trạng thái bàn
        table.setStatus("occupied");
        tableRepository.save(table);
        
        // 5. Lưu order
        Order savedOrder = orderRepository.save(order);
        
        return mapper.toOrderResponse(savedOrder);
    }
    
    
     // Lấy tất cả đơn hàng
     
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(mapper::toOrderResponse)
                .collect(Collectors.toList());
    }
    
    
     //Lấy đơn hàng theo ID
     
    public OrderResponse getOrderById(Integer id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        return mapper.toOrderResponse(order);
    }
    
    
    //Lấy các đơn đang hoạt động (pending, cooking, ready)
    
    public List<OrderResponse> getActiveOrders() {
        return orderRepository.findActiveOrders().stream()
                .map(mapper::toOrderResponse)
                .collect(Collectors.toList());
    }
    
    
     // Cập nhật trạng thái đơn hàng

    @Transactional
    public OrderResponse updateOrderStatus(Integer orderId, String newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        
        order.setStatus(newStatus);
        order.setUpdatedAt(LocalDateTime.now());
        
        // Nếu completed hoặc cancelled → bàn trống
        if ("completed".equals(newStatus) || "cancelled".equals(newStatus)) {
            RestaurantTable table = order.getTable();
            table.setStatus("available");
            tableRepository.save(table);
        }
        
        Order updatedOrder = orderRepository.save(order);
        return mapper.toOrderResponse(updatedOrder);
    }
    
    
     // Lấy đơn hàng theo bàn
     
    public List<OrderResponse> getOrdersByTable(Integer tableId) {
        return orderRepository.findByTableIdOrderByCreatedAtDesc(tableId).stream()
                .map(mapper::toOrderResponse)
                .collect(Collectors.toList());
    }
    
    
    // Hủy đơn hàng
    
    @Transactional
    public OrderResponse cancelOrder(Integer orderId) {
        return updateOrderStatus(orderId, "cancelled");
    }
    
    
     //Generate order number: ORD + timestamp
    
    private String generateOrderNumber() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return "ORD" + LocalDateTime.now().format(formatter);
    }
}