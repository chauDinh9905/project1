package com.restaurant.ordersystem.controller;

import com.restaurant.ordersystem.dto.request.CreateOrderRequest;
import com.restaurant.ordersystem.dto.response.OrderResponse;
import com.restaurant.ordersystem.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class OrderController {
    
    private final OrderService orderService;
    
    /*
      Tạo đơn hàng mới
      POST /api/orders
     */
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        OrderResponse response = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    /*
      Lấy tất cả đơn hàng
      GET /api/orders
     */
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        List<OrderResponse> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }
    
    /*
      Lấy đơn hàng theo ID
      GET /api/orders/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Integer id) {
        OrderResponse order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }
    
    /*
      Lấy các đơn đang hoạt động
      GET /api/orders/active
     */
    @GetMapping("/active")
    public ResponseEntity<List<OrderResponse>> getActiveOrders() {
        List<OrderResponse> orders = orderService.getActiveOrders();
        return ResponseEntity.ok(orders);
    }
    
    /**
      Cập nhật trạng thái đơn hàng
      PATCH /api/orders/{id}/status?status=cooking
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(
            @PathVariable Integer id,
            @RequestParam String status) {
        OrderResponse order = orderService.updateOrderStatus(id, status);
        return ResponseEntity.ok(order);
    }
    
    /*
      Lấy đơn hàng theo bàn
      GET /api/orders/table/{tableId}
     */
    @GetMapping("/table/{tableId}")
    public ResponseEntity<List<OrderResponse>> getOrdersByTable(@PathVariable Integer tableId) {
        List<OrderResponse> orders = orderService.getOrdersByTable(tableId);
        return ResponseEntity.ok(orders);
    }
}