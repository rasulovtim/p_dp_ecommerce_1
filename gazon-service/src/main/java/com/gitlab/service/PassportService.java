package com.gitlab.service;

import com.gitlab.dto.PassportDto;
import com.gitlab.mapper.BankCardMapper;
import com.gitlab.mapper.PassportMapper;
import com.gitlab.model.Passport;
import com.gitlab.repository.PassportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PassportService {

    private final PassportRepository passportRepository;

    private final PassportMapper passportMapper;

    public List<Passport> findAll() {
        return passportRepository.findAll();
    }

    public List<PassportDto> findAllDto() {
        List<Passport> passports = passportRepository.findAll();
        return passports.stream()
                .map(passportMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<Passport> findById(Long id) {
        return passportRepository.findById(id);
    }

    public Optional<PassportDto> findByIdDto(Long id) {
        return passportRepository.findById(id)
                .map(passportMapper::toDto);
    }

    public Passport save(Passport passport) {
        return passportRepository.save(passport);
    }

    public PassportDto saveDto(PassportDto passportDto) {
        Passport passport = passportMapper.toEntity(passportDto);
        Passport savedPassport = passportRepository.save(passport);
        return passportMapper.toDto(savedPassport);
    }

    public Optional<Passport> update(Long id, Passport passport) {
        Optional<Passport> optionalSavedPassport = findById(id);
        Passport savedPassport;

        if (optionalSavedPassport.isEmpty()) {
            return optionalSavedPassport;
        } else {
            savedPassport = optionalSavedPassport.get();
        }

        if (passport.getCitizenship() != null) {
            savedPassport.setCitizenship(passport.getCitizenship());
        }

        if (passport.getFirstName() != null) {
            savedPassport.setFirstName(passport.getFirstName());
        }

        if (passport.getLastName() != null) {
            savedPassport.setLastName(passport.getLastName());
        }

        if (passport.getPatronym() != null) {
            savedPassport.setPatronym(passport.getPatronym());
        }

        if (passport.getBirthDate() != null) {
            savedPassport.setBirthDate(passport.getBirthDate());
        }

        if (passport.getIssueDate() != null) {
            savedPassport.setIssueDate(passport.getIssueDate());
        }

        if (passport.getPassportNumber() != null) {
            savedPassport.setPassportNumber(passport.getPassportNumber());
        }

        if (passport.getIssuer() != null) {
            savedPassport.setIssuer(passport.getIssuer());
        }

        if (passport.getIssuerNumber() != null) {
            savedPassport.setIssuerNumber(passport.getIssuerNumber());
        }

        return Optional.of(passportRepository.save(savedPassport));
    }

    public Optional<PassportDto> updateDto(Long id, PassportDto passportDto) {
        Optional<Passport> optionalSavedPassport = findById(id);
        if (optionalSavedPassport.isEmpty()) {
            return Optional.empty();
        } else {
            Passport savedPassport = optionalSavedPassport.get();

            if (passportDto.getCitizenship() != null) {
                savedPassport.setCitizenship(passportDto.getCitizenship());
            }

            if (passportDto.getFirstName() != null) {
                savedPassport.setFirstName(passportDto.getFirstName());
            }

            if (passportDto.getLastName() != null) {
                savedPassport.setLastName(passportDto.getLastName());
            }

            if (passportDto.getPatronym() != null) {
                savedPassport.setPatronym(passportDto.getPatronym());
            }

            if (passportDto.getBirthDate() != null) {
                savedPassport.setBirthDate(passportDto.getBirthDate());
            }

            if (passportDto.getIssueDate() != null) {
                savedPassport.setIssueDate(passportDto.getIssueDate());
            }

            if (passportDto.getPassportNumber() != null) {
                savedPassport.setPassportNumber(passportDto.getPassportNumber());
            }

            if (passportDto.getIssuer() != null) {
                savedPassport.setIssuer(passportDto.getIssuer());
            }

            if (passportDto.getIssuerNumber() != null) {
                savedPassport.setIssuerNumber(passportDto.getIssuerNumber());
            }

            Passport updatedPassport = passportRepository.save(savedPassport);
            return Optional.ofNullable(passportMapper.toDto(updatedPassport));
        }
    }

    public Optional<Passport> delete(Long id) {
        Optional<Passport> optionalSavedPassport = findById(id);
        if (optionalSavedPassport.isEmpty()) {
            return optionalSavedPassport;
        } else {
            passportRepository.deleteById(id);
            return optionalSavedPassport;
        }
    }
    public Optional<PassportDto> deleteDto(Long id) {
        Optional<Passport> optionalSavedPassport = findById(id);
        if (optionalSavedPassport.isEmpty()) {
            return Optional.empty();
        } else {
            passportRepository.deleteById(id);
            return optionalSavedPassport.map(passportMapper::toDto);
        }
    }

}