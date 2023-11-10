package com.sparta.lv5.products;

import com.sparta.lv5.products.dto.ProductRegisterDto;
import com.sparta.lv5.products.dto.ProductResponseDto;
import com.sparta.lv5.products.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;


    public ProductResponseDto addProduct(ProductRegisterDto registerDto) {
        if (productRepository.existsByName(registerDto.getName()))
            throw new IllegalArgumentException("이미 등록된 상품입니다");
        Product newProduct = new Product(registerDto);
        return new ProductResponseDto(productRepository.save(newProduct));
    }
}
