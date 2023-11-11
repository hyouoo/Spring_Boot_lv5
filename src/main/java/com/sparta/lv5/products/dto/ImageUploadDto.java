package com.sparta.lv5.products.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@SuppressWarnings("unused")
@Getter
@NoArgsConstructor
public class ImageUploadDto {
    @NotBlank
    private String name;
    @NotBlank
    private String srcurl;
    @NotNull
    private Integer productId;
}
