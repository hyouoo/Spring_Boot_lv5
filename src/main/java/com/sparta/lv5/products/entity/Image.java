package com.sparta.lv5.products.entity;

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

    @Column(nullable = false, length = 500)
    private String srcurl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public Image(String name, String url, Product product) {
        this.name = name;
        this.srcurl = url;
        this.product = product;
        product.getImages().add(this);
    }

    public void delParents() {
        this.getProduct().getImages().remove(this);
    }
}
