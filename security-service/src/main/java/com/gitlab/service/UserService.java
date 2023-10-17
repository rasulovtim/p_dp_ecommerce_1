package com.gitlab.service;

import com.gitlab.dto.UserDto;
import com.gitlab.mapper.UserMapper;
import com.gitlab.model.Role;

import com.gitlab.model.User;
import com.gitlab.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<UserDto> findAllDto() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toDto).collect(Collectors.toList());
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public UserDto findByIdDto(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.map(userMapper::toDto).orElse(null);
    }

//    @Transactional
//    public User save(User user) {
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        user.setCreateDate(LocalDate.from(LocalDateTime.now()));
//        return userRepository.save(user);
//    }

    @Transactional
    public UserDto saveDto(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        user.setCreateDate(LocalDate.from(LocalDateTime.now()));
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    @Transactional
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

        if (user.getRolesSet() != null) {
            savedUser.setRolesSet(user.getRolesSet());
        }
        return Optional.of(userRepository.save(savedUser));
    }

    @Transactional
    public Optional<User> delete(Long id) {
        Optional<User> optionalSavedExample = findById(id);
        if (optionalSavedExample.isEmpty()) {
            return optionalSavedExample;
        } else {
            userRepository.deleteById(id);
            return optionalSavedExample;
        }
    }

    @Transactional
    public UserDto updateDto(Long id, UserDto userDto) {
        Optional<User> optionalSavedUser = userRepository.findById(id);
        if (optionalSavedUser.isEmpty()) {
            return null;
        }
        User savedUser = optionalSavedUser.get();

        savedUser = updateUserFields(savedUser, userDto);
        User updatedUser = userRepository.save(savedUser);
        return userMapper.toDto(updatedUser);
    }

    @Transactional
    public UserDto deleteDto(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            return null;
        }
        userRepository.deleteById(id);
        return userMapper.toDto(optionalUser.get());
    }

    private User updateUserFields(User user, UserDto userDto) {
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setSecurityQuestion(userDto.getSecurityQuestion());
        user.setAnswerQuestion(userDto.getAnswerQuestion());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());

        Set<Role> roles = userMapper.mapRoleSetToStringSet(userDto.getRoles());
        user.setRolesSet(roles);

        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username);
    }
}