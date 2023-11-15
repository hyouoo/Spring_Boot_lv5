package com.sparta.lv5.accounts.entity;

import com.sparta.lv5.accounts.dto.UserSignupRequestDto;
import com.sparta.lv5.wishes.entity.Wish;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static com.sparta.lv5.accounts.entity.UserRole.ADMIN;
import static com.sparta.lv5.accounts.entity.UserRole.USER;

@Entity
@Getter
@Table(name = "accounts")
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, length = 50)
    private String email;

    private String password;

    @Column(length = 1)
    private String gender;

    @Column(length = 50)
    private String phone;

    private String address;

    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    private Long kakaoId;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Wish> wishes;

    public Account(String password, UserSignupRequestDto requestDto) {
        email = requestDto.getEmail();
        this.password = password;
        gender = requestDto.getGender();
        phone = requestDto.getPhone();
        address = requestDto.getAddress();
        role = requestDto.isAdmin() ? ADMIN : USER;
        wishes = new ArrayList<>();
    }

    public Account(String email, String password, UserRole userRole, Long kakaoId) {
        this.email = email;
        this.password = password;
        this.role = userRole;
        this.kakaoId = kakaoId;
        wishes = new ArrayList<>();
    }

    public Account kakaoIdUpdate(Long kakaoId) {
        this.kakaoId = kakaoId;
        return this;
    }
}
