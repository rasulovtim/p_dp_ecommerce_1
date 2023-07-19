package com.gitlab.service;


import com.gitlab.model.User;
import com.gitlab.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public User save(User user) {
        user.setCreateDate(LocalDate.from(LocalDateTime.now()));
        return userRepository.save(user);
    }

    public Optional<User> update(Long id, User user) {
        Optional<User> optionalSavedUser = findById(id);
        User savedUser;
        if (optionalSavedUser.isEmpty()) {
            return optionalSavedUser;
        } else {
            savedUser = optionalSavedUser.get();
        }
        if (user.getEmail() != null) {
            savedUser.setEmail(user.getEmail());
        }
        if (user.getPassword() != null) {
            savedUser.setPassword(user.getPassword());
        }
        if (user.getSecurityQuestion() != null) {
            savedUser.setSecurityQuestion(user.getSecurityQuestion());
        }
        if (user.getAnswerQuestion() != null) {
            savedUser.setAnswerQuestion(user.getAnswerQuestion());
        }
        if (user.getFirstName() != null) {
            savedUser.setFirstName(user.getFirstName());
        }
        if (user.getLastName() != null) {
            savedUser.setLastName(user.getLastName());
        }
        if (user.getBirthDate() != null) {
            savedUser.setBirthDate(user.getBirthDate());
        }
        if (user.getGender() != null) {
            savedUser.setGender(user.getGender());
        }
        if (user.getPhoneNumber() != null) {
            savedUser.setPhoneNumber(user.getPhoneNumber());
        }
        if (user.getPassport() != null) {
            savedUser.setPassport(user.getPassport());
        }
        if (user.getCreateDate() == null) {
            savedUser.setCreateDate(LocalDate.now());
        }
        if (user.getBankCardsSet() != null) {
            savedUser.setBankCardsSet(user.getBankCardsSet());
        }
        if (user.getPersonalAddressSet() != null) {
            savedUser.setPersonalAddressSet(user.getPersonalAddressSet());
        }
        if (user.getRolesSet() != null) {
            savedUser.setRolesSet(user.getRolesSet());
        }
        return Optional.of(userRepository.save(savedUser));
    }

    public Optional<User> delete(Long id) {
        Optional<User> optionalSavedExample = findById(id);
        if (optionalSavedExample.isEmpty()) {
            return optionalSavedExample;
        } else {
            userRepository.deleteById(id);
            return optionalSavedExample;
        }
    }
}
