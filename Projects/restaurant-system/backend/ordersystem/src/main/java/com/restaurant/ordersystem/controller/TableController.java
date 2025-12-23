package com.restaurant.ordersystem.controller;

import com.restaurant.ordersystem.dto.response.TableResponse;
import com.restaurant.ordersystem.service.TableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tables")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TableController {
    
    private final TableService tableService;
    
    /*
      Lấy tất cả bàn
      GET /api/tables
     */
    @GetMapping
    public ResponseEntity<List<TableResponse>> getAllTables() {
        List<TableResponse> tables = tableService.getAllTables();
        return ResponseEntity.ok(tables);
    }
    
    /*
      Lấy bàn trống
      GET /api/tables/available
     */
    @GetMapping("/available")
    public ResponseEntity<List<TableResponse>> getAvailableTables() {
        List<TableResponse> tables = tableService.getAvailableTables();
        return ResponseEntity.ok(tables);
    }
    
    /*
      Cập nhật trạng thái bàn
      PATCH /api/tables/{id}/status?status=occupied
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<TableResponse> updateTableStatus(
            @PathVariable Integer id,
            @RequestParam String status) {
        TableResponse table = tableService.updateTableStatus(id, status);
        return ResponseEntity.ok(table);
    }
}