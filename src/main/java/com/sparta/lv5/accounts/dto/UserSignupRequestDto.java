package com.sparta.lv5.accounts.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
@SuppressWarnings("unused")
public class UserSignupRequestDto {
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[~!@#$%^&*()_+=])(?=\\S+$).{8,15}$",
            message = "비밀번호는 영문, 숫자, 특수기호를 조합하여 8자에서 15자로 입력해야 합니다.")
    private String password;

    @Size(max = 1, message = "M, F로 입력해 주세요.")
    private String gender;

    private String phone;

    private String address;

    private boolean admin;

    private String key;
}
