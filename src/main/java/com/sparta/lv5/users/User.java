package com.sparta.lv5.users;

import com.sparta.lv5.users.dto.UserSignupRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.sparta.lv5.users.UserRole.ADMIN;
import static com.sparta.lv5.users.UserRole.USER;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor
public class User {
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

    public User(String password, UserSignupRequestDto requestDto) {
        this.email = requestDto.getEmail();
        this.password = password;
        this.gender = requestDto.getGender();
        this.phone = requestDto.getPhone();
        this.address = requestDto.getAddress();
        this.role = requestDto.isAdmin() ? ADMIN : USER;
    }
}
