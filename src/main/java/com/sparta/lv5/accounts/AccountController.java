package com.sparta.lv5.accounts;

import com.sparta.lv5.accounts.dto.UserSignupRequestDto;
import com.sparta.lv5.accounts.entity.Account;
import com.sparta.lv5.common.security.MyUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("")
    public ResponseEntity<String> signupUser(@RequestBody @Valid UserSignupRequestDto requestDto) {
        Account newAccount = accountService.signupUser(requestDto);
        return ResponseEntity.created(URI.create("/api/user/login"))
                .body(String.format("%s(으)로 등록되었습니다.", newAccount.getRole()));
    }

    @DeleteMapping("")
    public ResponseEntity<String> deleteUser(@AuthenticationPrincipal MyUserDetails userDetails) {
        String email = userDetails.getAccount().getEmail();
        accountService.deleteUser(userDetails.getAccount());
        return ResponseEntity.accepted()
                .body(String.format("%s 계정의 탈퇴 요청이 정상적으로 처리되었습니다.", email));
    }
}
