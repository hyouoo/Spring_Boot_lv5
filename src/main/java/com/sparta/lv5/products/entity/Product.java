package com.sparta.lv5.products.entity;

import com.sparta.lv5.products.dto.ProductRegisterDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Product extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50, nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer amount;

    @Column(length = 500)
    private String info;

    @Column(length = 50)
    private String category;

    public Product(ProductRegisterDto registerDto) {
        name = registerDto.getName();
        price = registerDto.getPrice();
        amount = registerDto.getAmount();
        info = registerDto.getInfo();
        category = registerDto.getCategory();
    }
}
