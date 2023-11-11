package com.sparta.lv5.products.entity;

import com.sparta.lv5.products.dto.ProductRegisterDto;
import com.sparta.lv5.wishes.entity.Wish;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "product", orphanRemoval = true)
    private List<Image> images;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<Wish> wishes;

    public Product(ProductRegisterDto registerDto) {
        name = registerDto.getName();
        price = registerDto.getPrice();
        amount = registerDto.getAmount();
        info = registerDto.getInfo();
        category = registerDto.getCategory();
        images = new ArrayList<>();
        wishes = new ArrayList<>();
    }
}
