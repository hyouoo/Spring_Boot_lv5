package com.sparta.lv5.products.dto;

import com.sparta.lv5.products.entity.Image;
import lombok.Getter;

@Getter
public class ImageResponseDto {
    private final String name;
    private final String srcurl;

    public ImageResponseDto(Image image) {
        name = image.getName();
        srcurl = image.getSrcurl();
    }
}
