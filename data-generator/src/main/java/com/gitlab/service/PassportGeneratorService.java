package com.gitlab.service;

import com.gitlab.client.PassportGeneratorClient;
import com.gitlab.dto.PassportDto;
import com.gitlab.enums.Citizenship;
import com.gitlab.util.DataGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class PassportGeneratorService {

    private final PassportGeneratorClient passportGeneratorClient;

    public PassportDto generatePassport() {
        String additionalData = DataGenerator.generateRandomString(3);
        PassportDto passportDto = generatePassportData(additionalData);

        passportGeneratorClient.create(passportDto);
        return passportDto;
    }

    public PassportDto generatePassportData(String additionalData) {
        PassportDto passportDto = new PassportDto();
        passportDto.setCitizenship(Citizenship.RUSSIA);
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