package com.restaurant.ordersystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;  
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "categories") // lớp Category chính là bảng categories trong db
@Data      // tự động tạo getter, setter, tótring() và euqals()
@NoArgsConstructor  // cái này là tạo constructor rỗng
@AllArgsConstructor // tạo constructor với tất cả các tham số
public class Category {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) // id tự động tăng
    private Integer id;
    
    @Column(nullable = false, length = 100) // tương ứng với cột 
    private String name;  
    
    @Column(name = "sort_order") // tương ứng với cột sort_order
    private Integer sortOrder; 
    
    @JsonIgnore // khi chuyển đối tượng category sang json (lúc trả về cho frontend/api) thì không gômg list này để tránh vòng lặp vô tận do trong list này cũng chứa category
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL) // một category(khai vị, món chính, tráng miệng, đồ uống) có thể chứa nhiều món ăn (menuitem) 
    private List<MenuItem> menuItems;
}