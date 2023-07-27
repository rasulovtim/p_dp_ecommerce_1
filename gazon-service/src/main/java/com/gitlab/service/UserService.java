package com.gitlab.service;

import com.gitlab.dto.PersonalAddressDto;
import com.gitlab.mapper.PersonalAddressMapper;
import com.gitlab.mapper.PersonalAddressMapperImpl;
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

    private final PersonalAddressMapper personalAddressMapper;

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
            Passport newPassport = user.getPassport();
            Passport savePassport = savedUser.getPassport();
            if (savePassport != null) {
                newPassport.setId(savedUser.getPassport().getId());
            }
            savedUser.setPassport(newPassport);
        }
//
        if (user.getBankCardsSet() != null) {
            Set<BankCard> newBankCards = user.getBankCardsSet();
            Set<BankCard> savedBankCards = savedUser.getBankCardsSet();
            if (savedBankCards != null) {
                for (BankCard newBankCard : newBankCards) {
                    newBankCards.clear();
                    newBankCard.setId(user.getBankCardsSet()
                            .stream()
                            .mapToLong(BankCard::getId)
                            .findFirst()
                            .orElse(0L));
                    newBankCards.add(newBankCard);
                }
            }
            savedUser.setBankCardsSet(newBankCards);
        }

//        if ((user.getShippingAddressSet() != null)) {
//            Set<ShippingAddress> newShippingAddress = user.getShippingAddressSet();
//            Set<ShippingAddress> savedShippingAddress = savedUser.getShippingAddressSet();
//
//            if ((savedShippingAddress != null)) {
//                if (savedShippingAddress.getClass() == PersonalAddress.class) {
//                    Set<ShippingAddress> updatedShippingAddress = new HashSet<>();
//
//                    for (ShippingAddress newShipAddress : newShippingAddress) {
//                        ShippingAddress update = newShipAddress;
//                        update.setId(user.getShippingAddressSet().stream().mapToLong(ShippingAddress::getId).findFirst().orElse(0L));
//                        updatedShippingAddress.add(update);
//                    }
//                    savedUser.setShippingAddressSet(updatedShippingAddress);
//
//
//                } else if (savedShippingAddress.getClass().equals(Postomat.class)) {
//                    Set<ShippingAddress> updatedShippingAddress1 = new HashSet<>();
//
//                    for (ShippingAddress newShipAddress : newShippingAddress) {
//                        ShippingAddress update = newShipAddress;
//                        update.setId(user.getShippingAddressSet().stream().mapToLong(ShippingAddress::getId).findFirst().orElse(0L));
//                        updatedShippingAddress1.add(update);
//                    }
//                    savedUser.setShippingAddressSet(updatedShippingAddress1);
//
//                } else if (savedShippingAddress.getClass() == PickupPoint.class) {
//                    Set<ShippingAddress> updatedShippingAddress3 = new HashSet<>();
//
//                    for (ShippingAddress newShipAddress : newShippingAddress) {
//                        ShippingAddress update = newShipAddress;
//                        update.setId(user.getShippingAddressSet().stream().mapToLong(ShippingAddress::getId).findFirst().orElse(0L));
//                        updatedShippingAddress3.add(update);
//                    }
//                    savedUser.setShippingAddressSet(updatedShippingAddress3);
//                }
//            }
//        }
//        if (savedUser.getShippingAddressSet() != null) {
//            Set<PersonalAddressDto extends PersonalAddress> personalAddresses = new HashSet<>();
//            for (ShippingAddress newShippAddress : user.getShippingAddressSet()) {
//                if (newShippAddress.getClass() ==  PersonalAddress.class) {
//                    PersonalAddressDto newPerAddDto = personalAddressMapper.toDto((PersonalAddress) newShippAddress);
//                    newPerAddDto.setId(user.getShippingAddressSet().stream().mapToLong(ShippingAddress::getId).findFirst().orElse(0L));
//                    personalAddresses.add(newPerAddDto);
//                }
//            }
//            savedUser.setShippingAddressSet(personalAddresses);
//        }


//////////////
        if ((user.getShippingAddressSet() != null)) {
            Set<ShippingAddress> newShippingAddress = user.getShippingAddressSet();
            Set<ShippingAddress> savedShippingAddress = savedUser.getShippingAddressSet();

            if(savedShippingAddress != null) {
                    for (ShippingAddress newShipAddress : newShippingAddress) {
                        newShippingAddress.clear();
                        newShipAddress.setId(user.getShippingAddressSet().stream().mapToLong(ShippingAddress::getId).findFirst().orElse(0L));
                        newShippingAddress.add(newShipAddress);
                    }
                }
            savedUser.setShippingAddressSet(newShippingAddress);
        }


            if (user.getRolesSet() != null) {
                savedUser.getRolesSet().clear();
                savedUser.setRolesSet(user.getRolesSet());
            }
            return Optional.of(userRepository.save(savedUser));
        }

        public Optional<User> delete (Long id){
            Optional<User> optionalSavedExample = findById(id);
            if (optionalSavedExample.isEmpty()) {
                return optionalSavedExample;
            } else {
                userRepository.deleteById(id);
                return optionalSavedExample;
            }
        }
    }




