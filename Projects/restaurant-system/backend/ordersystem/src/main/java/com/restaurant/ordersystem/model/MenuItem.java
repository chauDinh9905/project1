package com.restaurant.ordersystem.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "menu_items") // lớp MenuItem tương ứng với bảng menu_item trong db
@Data // cái này để tự động tạo getter, setter, toString() và euqals()
@NoArgsConstructor // vẫn là để tạo contructor rỗng
@AllArgsConstructor // tạo constructor với tất cả các tham số
public class MenuItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // tương ứng cột id trong db và tự động tăng
    private Integer id; 
    
    @Column(nullable = false, length = 200) // tương ứng cột tên trong bảng trong db
    private String name;
    
    @Column(columnDefinition = "TEXT")  // tương ứng cột miêu tả món ăn trong bảng trong db
    private String description;
    
    @Column(nullable = false, precision = 10, scale = 2) // tương ứng cột giá tiền
    private BigDecimal price;
    
    @Column(length = 500)  //tương ứng cột gắn link hình ảnh
    private String image;
    
    @Column(nullable = false)
    private Boolean available = true;  // tương ứng cột cho biết trạng thái món ăn 
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    @JsonIgnoreProperties("menuItems")
    private Category category;
}