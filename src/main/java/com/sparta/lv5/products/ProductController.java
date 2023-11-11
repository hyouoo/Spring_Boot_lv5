package com.sparta.lv5.products;

import com.sparta.lv5.accounts.entity.UserRole;
import com.sparta.lv5.products.dto.ImageUploadDto;
import com.sparta.lv5.products.dto.ProductRegisterDto;
import com.sparta.lv5.products.dto.ProductResponseDto;
import com.sparta.lv5.products.entity.SortBy;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Secured(UserRole.Authority.ADMIN)
    @PostMapping("")
    public ProductResponseDto addProduct(@RequestBody @Valid ProductRegisterDto registerDto) {
        return productService.addProduct(registerDto);
    }

    @GetMapping("/{id}")
    public ProductResponseDto getProduct(@PathVariable Integer id) {
        return productService.getProduct(id);
    }

    @GetMapping("")
    public Page<ProductResponseDto> getProductList(Integer page, Integer size, String cat, SortBy sortBy, boolean isAsc) {
        return productService.getProductList(page - 1, size, cat, sortBy, isAsc);
    }

    @Secured(UserRole.Authority.ADMIN)
    @PostMapping("/images")
    public void addImage(@RequestBody @Valid ImageUploadDto uploadDto) {
        productService.addImage(uploadDto);
    }
}
