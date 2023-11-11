package com.sparta.lv5.wishes.dto;

import com.sparta.lv5.products.dto.ProductResponseDto;
import com.sparta.lv5.wishes.entity.Wish;
import lombok.Getter;

@Getter
public class WishListDto {
    private final ProductResponseDto productResponseDto;
    private final Integer amount;

    public WishListDto(Wish wish) {
        productResponseDto = new ProductResponseDto(wish.getProduct());
        amount = wish.getAmount();
    }
}
