package com.sparta.lv5.products;

import com.sparta.lv5.products.dto.ProductRegisterDto;
import com.sparta.lv5.products.entity.Image;
import com.sparta.lv5.products.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class ImageServiceTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void deleteImage() {
        // given
        ProductRegisterDto registerDto = new ProductRegisterDto("iphone", 100, 10, "good phone", "phone");
        Product product = new Product(registerDto);
        Image image = new Image("iphone15-2.jpeg", "https://mylv5bucket.s3.ap-northeast-2.amazonaws.com/images/4419f962-e6c0-4e91-8ac5-91606adc8ff0_iphone15-2.jpeg", product);

        // when
        Pattern pattern = Pattern.compile("com/(.*\\S)");
        Matcher matcher = pattern.matcher(image.getSrcurl());

        if (matcher.find()) {
            String key = matcher.group(1);
            assertEquals("images/4419f962-e6c0-4e91-8ac5-91606adc8ff0_iphone15-2.jpeg", key);
        } else {
            fail("일치하는 부분이 없음");
        }

        // then
    }
}