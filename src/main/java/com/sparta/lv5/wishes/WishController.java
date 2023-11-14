package com.sparta.lv5.wishes;

import com.sparta.lv5.accounts.entity.Account;
import com.sparta.lv5.common.tools.LoginUser;
import com.sparta.lv5.products.entity.Product;
import com.sparta.lv5.wishes.dto.MessageDto;
import com.sparta.lv5.wishes.dto.WishResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j(topic = "Account Check")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wishes")
public class WishController {

    private final WishService wishService;

    @PostMapping("")
    public MessageDto addWish(Integer productId, Integer amount, @LoginUser Account account) {
        log.info(String.valueOf(account.getEmail()));
        Product product = wishService.addWish(productId, amount, account);
        String msg = String.format("%s 상품 %d개가 추가되었습니다", product.getName(), amount);
        return new MessageDto(msg, HttpStatus.OK);
    }

    @GetMapping("")
    public WishResponseDto getWishList(@LoginUser Account account) {
        return wishService.getWishList(account);
    }

    @PutMapping("")
    public MessageDto modifyWish(Integer wishId, Integer amount) {
        Product product = wishService.modifyWish(wishId, amount);
        String msg = String.format("%s 상품의 수량이 %d개로 변경되었습니다", product.getName(), amount);
        return new MessageDto(msg, HttpStatus.OK);
    }

    @DeleteMapping("/{wishId}")
    public MessageDto deleteWish(@PathVariable Integer wishId) {
        Product product = wishService.deleteWish(wishId);
        String msg = String.format("%s 상품이 장바구니에서 삭제되었습니다", product.getName());
        return new MessageDto(msg, HttpStatus.OK);
    }
}
