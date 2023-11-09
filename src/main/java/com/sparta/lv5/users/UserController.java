package com.sparta.lv5.users;

import com.sparta.lv5.common.security.MyUserDetails;
import com.sparta.lv5.users.dto.UserSignupRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("")
    public ResponseEntity<String> signupUser(@RequestBody @Valid UserSignupRequestDto requestDto) {
        User newUser = userService.signupUser(requestDto);
        return ResponseEntity.created(URI.create("/api/user/login"))
                .body(String.format("%s(으)로 등록되었습니다.", newUser.getRole()));
    }

    @DeleteMapping("")
    public ResponseEntity<String> deleteUser(@AuthenticationPrincipal MyUserDetails userDetails) {
        User user = userDetails.getUser();
        userService.deleteUser(user);
        return ResponseEntity.accepted()
                .body(String.format("%s 계정의 탈퇴 요청이 정상적으로 처리되었습니다.", user.getEmail()));
    }
}
