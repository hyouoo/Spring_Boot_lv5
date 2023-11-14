package com.sparta.lv5.products;

import com.sparta.lv5.products.dto.ProductRegisterDto;
import com.sparta.lv5.products.dto.ProductResponseDto;
import com.sparta.lv5.products.entity.Product;
import com.sparta.lv5.products.entity.SortBy;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(readOnly = true)
    public ProductResponseDto getProduct(Integer id) {
        Product product = productRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 상품이 없습니다"));
        return new ProductResponseDto(product);
    }

    @Transactional(readOnly = true)
    public Page<ProductResponseDto> getProductList(Integer page, Integer size, String cat, SortBy sortBy, boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, String.valueOf(sortBy));
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Product> productPage = productRepository.findAllByCategory(cat, pageable);
        return productPage.map(ProductResponseDto::new);
    }
}
