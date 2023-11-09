package com.sparta.lv5.users;

import com.sparta.lv5.users.dto.UserSignupRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User signupUser(UserSignupRequestDto requestDto) {
        String email = requestDto.getEmail();
        String password = passwordEncoder.encode(requestDto.getPassword());
        // duplication check
        if (userRepository.findByEmail(email).isPresent()) {
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
        User newUser = new User(password, requestDto);
        return userRepository.save(newUser);
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }
}
