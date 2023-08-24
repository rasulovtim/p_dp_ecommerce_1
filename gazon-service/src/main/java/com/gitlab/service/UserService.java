package com.gitlab.service;

import com.gitlab.model.*;
import com.gitlab.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

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
    @Transactional
    public User save(User user) {
        user.setCreateDate(LocalDate.from(LocalDateTime.now()));
        return userRepository.save(user);
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
            var newPassport = user.getPassport();
            var savePassport = savedUser.getPassport();
            if (savePassport != null) {
                newPassport.setId(savedUser.getPassport().getId());
            }
            savedUser.setPassport(newPassport);
        }

        if (user.getShippingAddressSet() != null) {
            Set<ShippingAddress> newShippAddr = new HashSet<>();
            Set<ShippingAddress> savedShippAddr = savedUser.getShippingAddressSet();
            if(savedShippAddr != null){
                for (ShippingAddress address : user.getShippingAddressSet()){
                    for(ShippingAddress addressId : savedShippAddr){
                        Long shippAddress = addressId.getId();
                        address.setId(shippAddress);
                        address.setAddress(address.getAddress());
                        address.setDirections(address.getDirections());
                    }
                    newShippAddr.add(address);
                }
            }
            savedUser.setShippingAddressSet(newShippAddr);
        }

        if (user.getBankCardsSet() != null) {
            Set<BankCard> newCard = new HashSet<>();
            Set<BankCard> savedCard = savedUser.getBankCardsSet();
                if (savedCard != null) {
                     for (BankCard bankCard : user.getBankCardsSet()){
                         for (BankCard cardId : savedCard) {
                             Long bankCardId = cardId.getId();
                             bankCard.setId(bankCardId);
                         }
                         newCard.add(bankCard);
                     }
                }
            savedUser.setBankCardsSet(newCard);
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

}




