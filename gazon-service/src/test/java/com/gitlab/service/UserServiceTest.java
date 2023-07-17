package com.gitlab.service;

import com.gitlab.model.*;
import com.gitlab.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    @Test
    void should_find_all_users() {
        List<User> expectedResult = generateUsers();
        when(userRepository.findAll()).thenReturn(generateUsers());

        List<User> actualResult = userService.findAll();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void should_find_user_by_id() {
        long id = 1L;
        User expectedResult = generateUser();
        when(userRepository.findById(id)).thenReturn(Optional.of(expectedResult));

        Optional<User> actualResult = userService.findById(id);

        assertEquals(expectedResult, actualResult.orElse(null));
    }

    @Test
    void should_save_user() {
        User expectedResult = generateUser();
        when(userRepository.save(expectedResult)).thenReturn(expectedResult);

        User actualResult = userService.save(expectedResult);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void should_update_user() {
        long id = 1L;


        User userToUpdate = generateUser();
        User userBeforeUpdate = generateUserBefore();
        User updatedUser = generateUser();

        when(userRepository.findById(id)).thenReturn(Optional.of(userBeforeUpdate));
        when(userRepository.save(updatedUser)).thenReturn(updatedUser);

        Optional<User> actualResult = userService.update(id, userToUpdate);

        assertEquals(updatedUser, actualResult.orElse(null));
    }

    @Test
    void should_not_update_user_when_entity_not_found() {
        long id = 1L;

        User userToUpdate = generateUser(id);

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        Optional<User> actualResult = userService.update(id, userToUpdate);

        verify(userRepository, never()).save(any());
        assertNull(actualResult.orElse(null));
    }

    @Test
    void should_not_updated_user_field_if_null() {
        long id = 1L;

        User userToUpdate = generateUser(id);
        userToUpdate.setEmail(null);
        User userBeforeUpdate = generateUserBefore();

        when(userRepository.findById(id)).thenReturn(Optional.of(userBeforeUpdate));
        when(userRepository.save(userBeforeUpdate)).thenReturn(userBeforeUpdate);

        Optional<User> actualResult = userService.update(id, userToUpdate);

        verify(userRepository).save(userBeforeUpdate);
        assertEquals(userBeforeUpdate, actualResult.orElse(null));
    }

    @Test
    void should_delete_user() {
        long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.of(generateUser()));

        userService.delete(id);

        verify(userRepository).deleteById(id);
    }

    @Test
    void should_not_delete_user_when_entity_not_found() {
        long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        userService.delete(id);

        verify(userRepository, never()).deleteById(anyLong());
    }

    private List<User> generateUsers() {

        return List.of(
                generateUser(1L),
                generateUser(2L),
                generateUser(3L),
                generateUser(4L)
        );
    }

    private User generateUser(Long id) {
       User user = generateUser();
       user.setId(id);
       return user;
    }

    private User generateUser() {
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(new Role(1L, "ROLE_ADMIN"));

        Set<BankCard> bankCardSet = new HashSet<>();
        bankCardSet.add(new BankCard(1L, "0000000000000000", LocalDate.of(1900, 1, 1), 777));

        Set<ShippingAddress> shippingAddressesSet = new HashSet<>();
        shippingAddressesSet.add(new PersonalAddress());

        Passport passport = new Passport();

        return new User(1L,
                "user",
                "user",
                "answer",
                "question",
                "user",
                "user",
                LocalDate.of(1900, 1, 1),
                User.Gender.MALE,
                "89007777777",
                passport, LocalDate.of(1900,
                1, 1),
                bankCardSet,
                shippingAddressesSet,
                roleSet);
        }

    private User generateUserBefore() {
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(new Role(1L, "ROLE_USER"));

        Set<BankCard> bankCardSet = new HashSet<>();
        bankCardSet.add(new BankCard(1L, "1111111111111111", LocalDate.of(2000, 5, 15), 888));

        Set<ShippingAddress> shippingAddressesSet = new HashSet<>();
        shippingAddressesSet.add(new PersonalAddress());

        Passport passport = new Passport();

        return new User(1L,
                "userBefore",
                "userBefore",
                "answerBefore",
                "questionBefore",
                "userBefore",
                "userBefore",
                LocalDate.of(2000, 5, 15),
                User.Gender.FEMALE,
                "89007777777",
                passport, LocalDate.of(2000,
                5, 15),
                bankCardSet,
                shippingAddressesSet,
                roleSet);
    }
    }

