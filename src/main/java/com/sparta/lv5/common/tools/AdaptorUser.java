package com.sparta.lv5.common.tools;

import com.sparta.lv5.accounts.entity.Account;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Getter
public class AdaptorUser extends User {
    private final Account account;

    public AdaptorUser(Account account) {
        super(account.getEmail(), account.getPassword(), List.of(new SimpleGrantedAuthority(account.getRole().getAuthority())));
        this.account = account;
    }
}
