package com.gitlab.service;

import com.gitlab.model.PersonalAddress;
import com.gitlab.repository.PersonalAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonalAddressService {

    private final PersonalAddressRepository personalAddressRepository;

    public List<PersonalAddress> findAll() {
        return personalAddressRepository.findAll();
    }

    public Optional<PersonalAddress> findById(Long id) {
        return personalAddressRepository.findById(id);
    }

    public PersonalAddress save(PersonalAddress personalAddress) {
        return personalAddressRepository.save(personalAddress);
    }

    public Optional<PersonalAddress> update(Long id, PersonalAddress personalAddress) {
        Optional<PersonalAddress> optionalSavedAddress = findById(id);
        PersonalAddress savedPersonalAddress;
        if (optionalSavedAddress.isEmpty()) {
            return optionalSavedAddress;
        } else {
            savedPersonalAddress = optionalSavedAddress.get();
        }

        if (personalAddress.getDirections() != null) {
            savedPersonalAddress.setDirections(personalAddress.getDirections());
        }
        if (personalAddress.getDoorCode() != null) {
            savedPersonalAddress.setDoorCode(personalAddress.getDoorCode());
        }
        if (personalAddress.getPostCode() != null) {
            savedPersonalAddress.setPostCode(personalAddress.getPostCode());
        }
        if (personalAddress.getAddress() != null) {
            savedPersonalAddress.setAddress(personalAddress.getAddress());
        }
        if (personalAddress.getApartment() != null) {
            savedPersonalAddress.setApartment(personalAddress.getApartment());
        }
        if (personalAddress.getFloor() != null) {
            savedPersonalAddress.setFloor(personalAddress.getFloor());
        }
        if (personalAddress.getEntrance() != null) {
            savedPersonalAddress.setEntrance(personalAddress.getEntrance());
        }

        return Optional.of(personalAddressRepository.save(savedPersonalAddress));
    }

    public Optional<PersonalAddress> delete(Long id) {
        Optional<PersonalAddress> optionalSavedAddress = findById(id);
        if (optionalSavedAddress.isPresent()) {
            personalAddressRepository.deleteById(id);
        }
        return optionalSavedAddress;
    }
}
