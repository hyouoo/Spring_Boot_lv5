package com.sparta.lv5.products;

import com.sparta.lv5.accounts.entity.UserRole;
import com.sparta.lv5.products.dto.ImageResponseDto;
import com.sparta.lv5.products.dto.ProductRegisterDto;
import com.sparta.lv5.products.dto.ProductResponseDto;
import com.sparta.lv5.products.entity.SortBy;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final ImageService imageService;

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
    @PostMapping(value = "{productId}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ImageResponseDto addImage(@PathVariable Integer productId, @RequestParam MultipartFile file) {
        try {
            return imageService.addImage(productId, file);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
