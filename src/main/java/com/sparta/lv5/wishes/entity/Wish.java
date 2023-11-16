package com.sparta.lv5.wishes.entity;

import com.sparta.lv5.accounts.entity.Account;
import com.sparta.lv5.products.entity.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "wishes")
@NoArgsConstructor
public class Wish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Product product;

    public Wish(Account account, Product product, Integer amount) {
        this.account = account;
        this.product = product;
        this.amount = amount;
        product.getWishes().add(this);
        account.getWishes().add(this);
    }

    public void delParents() {
        this.getAccount().getWishes().remove(this);
        this.getProduct().getWishes().remove(this);
    }

    public void changeAmount(Integer amount) {
        this.amount = amount;
    }
}
