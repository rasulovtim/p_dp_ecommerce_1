package com.gitlab.service;

import com.gitlab.model.User;
import com.gitlab.repository.UserRepository;
import com.gitlab.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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
