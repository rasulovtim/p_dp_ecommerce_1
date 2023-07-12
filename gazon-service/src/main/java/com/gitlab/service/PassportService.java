package com.gitlab.service;

import com.gitlab.model.Passport;
import com.gitlab.repository.PassportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PassportService {

    private final PassportRepository passportRepository;

    public List<Passport> findAll() {
        return passportRepository.findAll();
    }

    public Optional<Passport> findById(Long id) {
        return passportRepository.findById(id);
    }

    public Passport save(Passport passport) {
        return passportRepository.save(passport);
    }

    public Optional<Passport> update(Long id, Passport passport) {
        Optional<Passport> optionalSavedPassport = findById(id);
        Passport savedPassport;

        if (optionalSavedPassport.isEmpty()) {
            return optionalSavedPassport;
        } else {
            savedPassport = optionalSavedPassport.get();
        }

        savedPassport.setPatronym(passport.getPatronym());

        if (passport.getCitizenship() != null) {
            savedPassport.setCitizenship(passport.getCitizenship());
        }

        if (passport.getFirstName() != null) {
            savedPassport.setFirstName(passport.getFirstName());
        }

        if (passport.getLastName() != null) {
            savedPassport.setLastName(passport.getLastName());
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


    public Optional<Passport> delete(Long id) {
        Optional<Passport> optionalSavedPassport = findById(id);
        if (optionalSavedPassport.isEmpty()) {
            return optionalSavedPassport;
        } else {
            passportRepository.deleteById(id);
            return optionalSavedPassport;
        }
    }

}
