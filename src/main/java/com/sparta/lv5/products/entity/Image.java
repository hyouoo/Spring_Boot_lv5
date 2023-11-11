package com.sparta.lv5.products.entity;

import com.sparta.lv5.products.dto.ImageUploadDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "images")
@NoArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String srcurl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public Image(ImageUploadDto uploadDto, Product product) {
        name = uploadDto.getName();
        srcurl = uploadDto.getSrcurl();
        this.product = product;
        product.getImages().add(this);
    }
}
