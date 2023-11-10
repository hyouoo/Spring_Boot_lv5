package com.sparta.lv5.products.dto;

import com.sparta.lv5.products.entity.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductResponseDto {
    private String name;
    private Integer price;
    private Integer amount;
    private String info;
    private String category;

    public ProductResponseDto(ProductRegisterDto registerDto) {
        name = registerDto.getName();
        price = registerDto.getPrice();
        amount = registerDto.getAmount();
        info = registerDto.getInfo();
        category = registerDto.getCategory();
    }

    public ProductResponseDto(Product product) {
        name = product.getName();
        price = product.getPrice();
        amount = product.getAmount();
        info = product.getInfo();
        category = product.getCategory();
    }
}
