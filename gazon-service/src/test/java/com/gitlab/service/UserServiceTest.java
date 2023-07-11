package com.gitlab.service;

import com.gitlab.model.*;
import com.gitlab.model.enums.Gender;
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

        Set<Role> roleSet = new HashSet<>();
        roleSet.add(new Role(1L, "ROLE_ADMIN"));
        Set<BankCard> bankCardSet = new HashSet<>();
        bankCardSet.add(new BankCard(1L, "0000000000000000", LocalDate.of(1900, 1, 1), 777));
        Set<ShippingAddress> shippingAddressesSet = new HashSet<>();
        shippingAddressesSet.add(new ShippingAddress(1L, "address","directions"));


        User userToUpdate = generateUser();
        User userBeforeUpdate = new User(id, "mail1@mail.ru", "user1", "answer1", "question1", "user1", "user1", LocalDate.of(1900, 1, 1), Gender.MALE, "89007777778", new Passport(1L, "7777777778"), LocalDate.of(1900, 2, 2), bankCardSet, shippingAddressesSet, roleSet);
        User updatedUser = generateUser();

        when(userRepository.findById(id)).thenReturn(Optional.of(userBeforeUpdate));
        when(userRepository.save(updatedUser)).thenReturn(updatedUser);

        Optional<User> actualResult = userService.update(id, userToUpdate);

        assertEquals(updatedUser, actualResult.orElse(null));
    }

    @Test
    void should_not_update_user_when_entity_not_found() {
        long id = 1L;
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(new Role(1L, "ROLE_ADMIN"));
        Set<BankCard> bankCardSet = new HashSet<>();
        bankCardSet.add(new BankCard(1L, "0000000000000000", LocalDate.of(1900, 1, 1), 777));
        Set<ShippingAddress> shippingAddressesSet = new HashSet<>();
        shippingAddressesSet.add(new ShippingAddress(1L, "address","directions"));

        User userToUpdate = new User(id, "mail@mail.ru", "user", "answer", "question", "user", "user", LocalDate.of(1900, 1, 1), Gender.MALE, "89007777777", new Passport(1L, "7777777777"), LocalDate.of(1900, 1, 1), bankCardSet, shippingAddressesSet, roleSet);

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        Optional<User> actualResult = userService.update(id, userToUpdate);

        verify(userRepository, never()).save(any());
        assertNull(actualResult.orElse(null));
    }

    @Test
    void should_not_updated_user_field_if_null() {
        long id = 1L;
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(new Role(1L, "ROLE_ADMIN"));
        Set<BankCard> bankCardSet = new HashSet<>();
        bankCardSet.add(new BankCard(1L, "0000000000000000", LocalDate.of(1900, 1, 1), 777));
        Set<ShippingAddress> shippingAddressesSet = new HashSet<>();
        shippingAddressesSet.add(new ShippingAddress(1L, "address","directions"));

        User userToUpdate = new User(id, null, "user", "answer", "question", "user", "user", LocalDate.of(1900, 1, 1), Gender.MALE, "89007777777", new Passport(1L, "7777777777"), LocalDate.of(1900, 1, 1), bankCardSet, shippingAddressesSet, roleSet);

        User userBeforeUpdate = new User(id, "user1", "user1", "answer1", "question1", "user1", "user1", LocalDate.of(1900, 1, 1), Gender.MALE, "89007777777", new Passport(1L, "7777777777"), LocalDate.of(1900, 1, 1), bankCardSet, shippingAddressesSet, roleSet);

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
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(new Role(1L, "ROLE_ADMIN"));
        Set<BankCard> bankCardSet = new HashSet<>();
        bankCardSet.add(new BankCard(1L, "0000000000000000", LocalDate.of(1900, 1, 1), 777));
        Set<ShippingAddress> shippingAddressesSet = new HashSet<>();
        shippingAddressesSet.add(new ShippingAddress(1L, "address","directions"));

        return List.of(
                new User(1L, "mail@mail.ru", "user", "answer", "question", "user", "user", LocalDate.of(1900, 1, 1), Gender.MALE, "89007777777", new Passport(1L, "7777777777"), LocalDate.of(1900, 1, 1), bankCardSet, shippingAddressesSet, roleSet),
                new User(2L, "mail@mail.ru", "user", "answer", "question", "user", "user", LocalDate.of(1900, 1, 1), Gender.MALE, "89007777777", new Passport(2L, "7777777777"), LocalDate.of(1900, 1, 1), bankCardSet, shippingAddressesSet, roleSet),
                new User(3L, "mail@mail.ru", "user", "answer", "question", "user", "user", LocalDate.of(1900, 1, 1), Gender.MALE, "89007777777", new Passport(3L, "7777777777"), LocalDate.of(1900, 1, 1), bankCardSet, shippingAddressesSet, roleSet),
                new User(4L, "mail@mail.ru", "user", "answer", "question", "user", "user", LocalDate.of(1900, 1, 1), Gender.MALE, "89007777777", new Passport(4L, "7777777777"), LocalDate.of(1900, 1, 1), bankCardSet, shippingAddressesSet, roleSet)
        );
    }

    private User generateUser() {
        Set<Role> roleSet = new HashSet<>();
            roleSet.add(new Role(1L, "ROLE_ADMIN"));
            Set<BankCard> bankCardSet = new HashSet<>();
            bankCardSet.add(new BankCard(1L, "0000000000000000", LocalDate.of(1900, 1, 1), 777));
            Set<ShippingAddress> shippingAddressesSet = new HashSet<>();
            shippingAddressesSet.add(new ShippingAddress(1L, "address","directions"));
        return new User(1L, "mail@mail.ru", "user", "answer", "question", "user", "user", LocalDate.of(1900, 1, 1), Gender.MALE, "89007777777", new Passport(4L, "7777777777"), LocalDate.of(1900, 1, 1), bankCardSet, shippingAddressesSet, roleSet);
        }
    }

