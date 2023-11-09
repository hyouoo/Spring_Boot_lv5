package com.sparta.lv5.common.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

//@Component
public class CustomAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errorMessage;

        if (exception instanceof BadCredentialsException) {
            errorMessage = "비밀번호가 틀렸습니다.";
        } else if (exception instanceof UsernameNotFoundException) {
            errorMessage = "존재하지 않는 계정입니다. 회원가입 후 로그인해주세요.";
        } else if (exception instanceof AccountExpiredException) {
            errorMessage = "만료된 계정입니다.";
        } else if (exception instanceof CredentialsExpiredException) {
            errorMessage = "만료된 비밀번호입니다.";
        } else {
            errorMessage = "알 수 없는 오류로 로그인 요청을 처리할 수 없습니다.";
        }

        errorMessage = URLEncoder.encode(errorMessage, StandardCharsets.UTF_8);
        request.setAttribute("message", errorMessage);
        request.getRequestDispatcher(String.valueOf(URI.create("/api/user/login"))).forward(request, response);
    }
}
