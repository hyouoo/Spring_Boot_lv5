package com.sparta.lv5.accounts;

import com.sparta.lv5.accounts.dto.UserSignupRequestDto;
import com.sparta.lv5.accounts.entity.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public Account signupUser(UserSignupRequestDto requestDto) {
        String email = requestDto.getEmail();
        String password = passwordEncoder.encode(requestDto.getPassword());
        // duplication check
        if (accountRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("중복된 Email입니다.");
        }
        // admin key check
        if (requestDto.isAdmin()) {
            String ADMIN_KEY = "this%20is%20admin%20key";
            if (!requestDto.getKey().equals(ADMIN_KEY)) {
                throw new IllegalArgumentException("관리자 암호가 틀렸습니다.");
            }
        }
        // signup new user
        Account newAccount = new Account(password, requestDto);
        return accountRepository.save(newAccount);
    }

    public void deleteUser(Account account) {
        accountRepository.delete(account);
    }
}
