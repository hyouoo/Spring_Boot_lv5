package com.sparta.lv5.common.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

@SuppressWarnings("unused")
//@Component
public class CustomAuthFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        String exceptionMsg;

        if (exception instanceof BadCredentialsException) {
            exceptionMsg = "비밀번호가 틀렸습니다.";
        } else if (exception instanceof UsernameNotFoundException) {
            exceptionMsg = "존재하지 않는 계정입니다. 회원가입 후 로그인해주세요.";
        } else if (exception instanceof AccountExpiredException) {
            exceptionMsg = "만료된 계정입니다.";
        } else if (exception instanceof CredentialsExpiredException) {
            exceptionMsg = "만료된 비밀번호입니다.";
        } else {
            exceptionMsg = "알 수 없는 오류로 로그인 요청을 처리할 수 없습니다.";
        }

        ErrorMessage errorMessage = new ErrorMessage(exceptionMsg, HttpStatus.UNAUTHORIZED);

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(errorMessage));
    }
}
