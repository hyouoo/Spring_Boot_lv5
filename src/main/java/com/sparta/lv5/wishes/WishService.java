package com.sparta.lv5.wishes;

import com.sparta.lv5.accounts.AccountRepository;
import com.sparta.lv5.accounts.entity.Account;
import com.sparta.lv5.products.ProductRepository;
import com.sparta.lv5.products.entity.Product;
import com.sparta.lv5.wishes.dto.WishListDto;
import com.sparta.lv5.wishes.dto.WishResponseDto;
import com.sparta.lv5.wishes.entity.Wish;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishService {

    private final WishRepository wishRepository;
    private final ProductRepository productRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public Product addWish(Integer productId, Integer amount, Account account) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new NullPointerException("해당 상품이 없습니다")
        );
        Account chkAccount = accountRepository.findById(account.getId()).orElseThrow(
                () -> new NullPointerException("잘못된 사용자입니다")
        );
        Wish newWish = new Wish(chkAccount, product, amount);
        wishRepository.save(newWish);
        return product;
    }

    @Transactional(readOnly = true)
    public WishResponseDto getWishList(Account account) {
        List<WishListDto> wishListDtos = wishRepository.findAllByAccountId(account.getId()).stream()
                .map(WishListDto::new).toList();
        Double totalPrice = wishListDtos.stream()
                .mapToDouble(wish -> wish.getProductResponseDto().getPrice() * wish.getAmount())
                .sum();
        return new WishResponseDto(wishListDtos, totalPrice);
    }


    @Transactional
    public Product modifyWish(Integer wishId, Integer amount) {
        Wish wish = wishRepository.findById(wishId).orElseThrow(
                () -> new IllegalArgumentException("잘못된 요청입니다.")
        );
        wish.changeAmount(amount);
        return wish.getProduct();
    }

    public Product deleteWish(Integer wishId) {
        Wish wish = wishRepository.findById(wishId).orElseThrow(
                () -> new IllegalArgumentException("잘못된 요청입니다.")
        );
        wishRepository.delete(wish);
        return wish.getProduct();
    }
}
