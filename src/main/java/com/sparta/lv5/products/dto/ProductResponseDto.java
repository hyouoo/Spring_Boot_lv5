package com.sparta.lv5.products.dto;

import com.sparta.lv5.products.entity.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ProductResponseDto {
    private String name;
    private Integer price;
    private Integer amount;
    private String info;
    private String category;
    private List<ImageResponseDto> images;

    public ProductResponseDto(Product product) {
        name = product.getName();
        price = product.getPrice();
        amount = product.getAmount();
        info = product.getInfo();
        category = product.getCategory();
        images = product.getImages().stream().map(ImageResponseDto::new).toList();
    }
}
