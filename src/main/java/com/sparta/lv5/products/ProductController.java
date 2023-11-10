package com.sparta.lv5.products;

import com.sparta.lv5.products.dto.ProductRegisterDto;
import com.sparta.lv5.products.dto.ProductResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    @PostMapping("")
    public ProductResponseDto addProduct(@RequestBody @Valid ProductRegisterDto registerDto) {
        return productService.addProduct(registerDto);
    }
}
