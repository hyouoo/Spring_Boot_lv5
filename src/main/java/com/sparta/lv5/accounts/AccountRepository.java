package com.sparta.lv5.accounts;

import com.sparta.lv5.accounts.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<Account> findByKakaoId(Long kakaoId);

}
