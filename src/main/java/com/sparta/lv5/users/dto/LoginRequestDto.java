package com.sparta.lv5.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
@SuppressWarnings("unused")
public class LoginRequestDto {
    @Email
    private String email;
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[~!@#$%^&*()_+=])(?=\\S+$).{8,15}$",
            message = "비밀번호는 영문, 숫자, 특수기호를 조합하여 8자에서 15자로 입력해야 합니다.")
    private String password;
}
