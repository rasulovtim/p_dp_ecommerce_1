package com.gitlab.service;

import com.gitlab.model.User;
import com.gitlab.repository.UserRepository;
import com.gitlab.util.JwtUtil;
import com.nimbusds.openid.connect.sdk.claims.Gender;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreateDate(LocalDate.from(LocalDateTime.now()));
        user.setBirthDate(LocalDate.now());
        user.setGender(Gender.MALE);
        user.setPhoneNumber("12345");
        return userRepository.save(user);
    }

    public String generateToken(String username) {
        return jwtUtil.generateToken(username);
    }

    public void validateToken(String token) {
        jwtUtil.validateToken(token);
    }

}
