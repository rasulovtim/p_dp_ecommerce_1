package com.gitlab.service;

import com.gitlab.client.PassportDtoGeneratorClient;
import com.gitlab.dto.PassportDto;
import com.gitlab.model.Passport;
import com.gitlab.util.DataGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class PassportDtoGeneratorService {
    private final PassportDtoGeneratorClient passportDtoGeneratorClient;

    public PassportDto generatePassportDto() {
        String additionalData = DataGenerator.generateRandomString(3);
        PassportDto passportDto = generatePassportDtoData(additionalData);

        passportDtoGeneratorClient.create(passportDto);
        return passportDto;
    }

    public PassportDto generatePassportDtoData(String additionalData) {
        PassportDto passportDto = new PassportDto();
        passportDto.setCitizenship(Passport.Citizenship.RUSSIA);
        passportDto.setFirstName("user" + additionalData);
        passportDto.setLastName("user" + additionalData);
        passportDto.setBirthDate(LocalDate.of(2000, 5, 15));
        passportDto.setIssueDate(LocalDate.of(2000, 5, 15));

        String passportNumber = DataGenerator.generateRandomString(5);
        passportDto.setPassportNumber(passportNumber);

        passportDto.setIssuer("isuer" + additionalData);
        passportDto.setIssuerNumber("issurN" + additionalData);

        return passportDto;
    }

}
