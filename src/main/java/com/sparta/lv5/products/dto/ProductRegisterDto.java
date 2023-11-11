package com.sparta.lv5.products.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductRegisterDto {
    @NotBlank(message = "상품명은 필수 입력값입니다")
    private final String name;

    @NotNull(message = "가격을 입력해 주세요")
    @PositiveOrZero(message = "양수를 입력해 주세요")
    private final Integer price;

    @NotNull(message = "수량을 입력해 주세요")
    @PositiveOrZero(message = "수량은 양수로 입력해야 합니다")
    private final Integer amount;

    private final String info;

    private final String category;
}
