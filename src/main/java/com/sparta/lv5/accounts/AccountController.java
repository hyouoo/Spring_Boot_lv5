package com.sparta.lv5.accounts;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.lv5.accounts.dto.UserSignupRequestDto;
import com.sparta.lv5.accounts.entity.Account;
import com.sparta.lv5.common.security.JwtUtil;
import com.sparta.lv5.common.tools.LoginUser;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Slf4j(topic = "AccountController")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class AccountController {

    private final AccountService accountService;
    private final KakaoService kakaoService;

    @PostMapping("")
    public ResponseEntity<String> signupUser(@RequestBody @Valid UserSignupRequestDto requestDto) {
        Account newAccount = accountService.signupUser(requestDto);
        return ResponseEntity.created(URI.create("/api/user/login"))
                .body(String.format("%s(으)로 등록되었습니다.", newAccount.getRole()));
    }

    @DeleteMapping("")
    public ResponseEntity<String> deleteUser(@LoginUser Account account) {
        log.info(account.getEmail());
        String email = account.getEmail();
        accountService.deleteUser(account);
        return ResponseEntity.accepted()
                .body(String.format("%s 계정의 탈퇴 요청이 정상적으로 처리되었습니다.", email));
    }

    @GetMapping("/kakao/callback")
    public void kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        String token = kakaoService.kakaoLogin(code);
        Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, token.substring(7));
        cookie.setPath("/");
        response.addCookie(cookie);
    }

}
