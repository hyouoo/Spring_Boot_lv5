package com.sparta.lv5.accounts;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.lv5.accounts.dto.KakaoUserInfoDto;
import com.sparta.lv5.accounts.entity.Account;
import com.sparta.lv5.accounts.entity.UserRole;
import com.sparta.lv5.common.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KakaoService {
    private final AccountRepository accountRepository;
    private final RestTemplate restTemplate;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Value("${kakao.restapi.key}")
    private String kakaoKey;
    @Value("${kakao.login.redirect}")
    private String redirectUri;

    public String kakaoLogin(String code) throws JsonProcessingException {
        // 1. 인가 코드로 access token 요청
        String accessToken = getToken(code);
        // 2. token으로 카카오 API 호출 -> 사용자 정보 가져오기
        KakaoUserInfoDto kakaoUserInfo = getKakaoUserInfo(accessToken);
        // 3. 필요시 회원 가입
        Account kakaoAccount = registerKakaoAccountIfNeeded(kakaoUserInfo);
        // 4. JWT 반환
        String createToken = jwtUtil.createToken(kakaoAccount.getEmail(), kakaoAccount.getRole());

        return createToken;
    }

    private String getToken(String code) throws JsonProcessingException {
        URI uri = UriComponentsBuilder
                .fromUriString("https://kauth.kakao.com")
                .path("/oauth/token")
                .encode()
                .build()
                .toUri();
        // creat HTTP header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        // create HTTP body
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authrization_code");
        body.add("client_id", kakaoKey);
        body.add("redirect_uri", redirectUri);
        body.add("code", code);

        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
                .post(uri)
                .headers(headers)
                .body(body);
        // HTTP 요청 보내기
        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        JsonNode jsonNode = new ObjectMapper().readTree(responseEntity.getBody());
        return jsonNode.get("access_token").asText();
    }

    private KakaoUserInfoDto getKakaoUserInfo(String accessToken) throws JsonProcessingException {
        // build request url
        URI uri = UriComponentsBuilder
                .fromUriString("https://kapi.kakao.com")
                .path("/v2/user/me")
                .encode()
                .build()
                .toUri();
        // create HTTP header
        HttpHeaders headers = new HttpHeaders();
        headers.add(JwtUtil.AUTHORIZATION_HEADER, JwtUtil.BEARER_PREFIX + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
                .post(uri)
                .headers(headers)
                .body(new LinkedMultiValueMap<>()
                );
        // request to kakao server and get response
        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

        JsonNode jsonNode = new ObjectMapper().readTree(responseEntity.getBody());
        Long id = jsonNode.get("id").asLong();
        String nickname = jsonNode.get("properties").get("nickname").asText();
        String email = jsonNode.get("kakao_account").get("email").asText();

        return new KakaoUserInfoDto(id, nickname, email);
    }

    private Account registerKakaoAccountIfNeeded(KakaoUserInfoDto kakaoUserInfoDto) {
        Long kakaoId = kakaoUserInfoDto.getId();
        Account kakaoAccount = accountRepository.findByKakaoId(kakaoId).orElse(null);

        if (kakaoAccount == null) {
            String kakaoEmail = kakaoUserInfoDto.getEmail();
            Account sameEmailAccount = accountRepository.findByEmail(kakaoEmail).orElse(null);
            if (sameEmailAccount != null) {
                kakaoAccount = sameEmailAccount;
                // 기존 회원정보에 카카오 Id 추가
                kakaoAccount = kakaoAccount.kakaoIdUpdate(kakaoId);
            } else {
                // 신규 회원가입
                // password: random UUID
                String password = UUID.randomUUID().toString();
                String encodedPassword = passwordEncoder.encode(password);

                // email: kakao email
                String email = kakaoUserInfoDto.getEmail();

                kakaoAccount = new Account(email, encodedPassword, UserRole.USER, kakaoId);
            }

            accountRepository.save(kakaoAccount);
        }
        return kakaoAccount;
    }
}

