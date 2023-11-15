package com.sparta.lv5.accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoUserInfoDto {
    private Long id;
    private String nickname;
    private String email;
}
