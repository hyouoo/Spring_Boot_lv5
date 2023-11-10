package com.sparta.lv5.common.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.lv5.common.exception.ErrorMessage;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;

import java.io.IOException;

@SuppressWarnings("unused")
//@Component
@RequiredArgsConstructor
public class AuthenticationEvents {

    private final HttpServletResponse response;

    //    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent failureEvent) throws IOException {
        ErrorMessage errorMessage = new ErrorMessage(failureEvent.getException().getMessage(), HttpStatus.UNAUTHORIZED);
        String msg = new ObjectMapper().writeValueAsString(errorMessage);

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(msg);
    }
}
