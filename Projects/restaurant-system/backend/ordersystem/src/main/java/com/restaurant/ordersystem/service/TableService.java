package com.restaurant.ordersystem.service;

import com.restaurant.ordersystem.dto.response.TableResponse;
import com.restaurant.ordersystem.model.RestaurantTable;
import com.restaurant.ordersystem.repository.TableRepository;
import com.restaurant.ordersystem.util.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TableService {
    
    private final TableRepository tableRepository;
    private final DtoMapper mapper;
    
    
     //Lấy tất cả bàn
    
    public List<TableResponse> getAllTables() {
        return tableRepository.findAll().stream()
                .map(mapper::toTableResponse)
                .collect(Collectors.toList());
    }
    
    
     // Lấy bàn trống
    
    public List<TableResponse> getAvailableTables() {
        return tableRepository.findByStatus("available").stream()
                .map(mapper::toTableResponse)
                .collect(Collectors.toList());
    }
    
    
     // Cập nhật trạng thái bàn
     
    @Transactional
    public TableResponse updateTableStatus(Integer tableId, String status) {
        RestaurantTable table = tableRepository.findById(tableId)
                .orElseThrow(() -> new RuntimeException("Table not found with id: " + tableId));
        
        table.setStatus(status);
        RestaurantTable updatedTable = tableRepository.save(table);
        
        return mapper.toTableResponse(updatedTable);
    }
}