package com.gitlab.service;

import com.gitlab.dto.PersonalAddressDto;
import com.gitlab.mapper.PersonalAddressMapper;
import com.gitlab.model.PersonalAddress;
import com.gitlab.repository.PersonalAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonalAddressService {

    private final PersonalAddressRepository personalAddressRepository;

    private final PersonalAddressMapper personalAddressMapper;


    public List<PersonalAddress> findAll() {
        return personalAddressRepository.findAll();
    }

    public List<PersonalAddressDto> findAllDto() {
        List<PersonalAddress> personalAddresses = personalAddressRepository.findAll();
        return personalAddresses.stream()
                .map(personalAddressMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<PersonalAddress> findById(Long id) {
        return personalAddressRepository.findById(id);
    }

    public Optional<PersonalAddressDto> findByIdDto(Long id) {
        return personalAddressRepository.findById(id)
                .map(personalAddressMapper::toDto);
    }

    public PersonalAddress save(PersonalAddress personalAddress) {
        return personalAddressRepository.save(personalAddress);
    }

    public PersonalAddressDto saveDto(PersonalAddressDto personalAddressDto) {
        PersonalAddress personalAddress = personalAddressMapper.toEntity(personalAddressDto);
        return personalAddressMapper.toDto(personalAddressRepository.save(personalAddress));
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

    public Optional<PersonalAddressDto> updateDto(Long id, PersonalAddressDto personalAddressDto) {
        Optional<PersonalAddress> optionalSavedAddress = personalAddressRepository.findById(id);

        if (optionalSavedAddress.isEmpty()) {
            return Optional.empty();
        }

        PersonalAddress savedPersonalAddress = optionalSavedAddress.get();

        if (personalAddressDto.getDirections() != null) {
            savedPersonalAddress.setDirections(personalAddressDto.getDirections());
        }
        if (personalAddressDto.getDoorCode() != null) {
            savedPersonalAddress.setDoorCode(personalAddressDto.getDoorCode());
        }
        if (personalAddressDto.getPostCode() != null) {
            savedPersonalAddress.setPostCode(personalAddressDto.getPostCode());
        }
        if (personalAddressDto.getAddress() != null) {
            savedPersonalAddress.setAddress(personalAddressDto.getAddress());
        }
        if (personalAddressDto.getApartment() != null) {
            savedPersonalAddress.setApartment(personalAddressDto.getApartment());
        }
        if (personalAddressDto.getFloor() != null) {
            savedPersonalAddress.setFloor(personalAddressDto.getFloor());
        }
        if (personalAddressDto.getEntrance() != null) {
            savedPersonalAddress.setEntrance(personalAddressDto.getEntrance());
        }

        PersonalAddress updatedPersonalAddress = personalAddressRepository.save(savedPersonalAddress);
        return Optional.ofNullable(personalAddressMapper.toDto(updatedPersonalAddress));
    }


    public Optional<PersonalAddress> delete(Long id) {
        Optional<PersonalAddress> optionalSavedAddress = findById(id);
        if (optionalSavedAddress.isPresent()) {
            personalAddressRepository.deleteById(id);
        }
        return optionalSavedAddress;
    }

    public Optional<PersonalAddressDto> deleteDto(Long id) {
        Optional<PersonalAddress> optionalSavedAddress = personalAddressRepository.findById(id);

        if (optionalSavedAddress.isPresent()) {
            personalAddressRepository.deleteById(id);
        }

        return optionalSavedAddress.map(personalAddressMapper::toDto);
    }

}
