package com.sparta.lv5.wishes.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class WishResponseDto {
    private final List<WishListDto> wishListDtos;
    private final Double totalPrice;

    public WishResponseDto(List<WishListDto> wishListDtos, Double totalPrice) {
        this.wishListDtos = wishListDtos;
        this.totalPrice = totalPrice;
    }
}
