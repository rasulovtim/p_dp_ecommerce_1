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
        PassportDto passportDto = generatePassportData();
        passportGeneratorClient.create(passportDto);

        return passportDto;
    }

    public PassportDto generatePassportData() {
        PassportDto passportDto = new PassportDto();

        passportDto.setCitizenship(Citizenship.RUSSIA);
        passportDto.setFirstName("user" + DataGenerator.generateRandomString(3));
        passportDto.setLastName("user" + DataGenerator.generateRandomString(3));
        passportDto.setPatronym("patronym" + DataGenerator.generateRandomString(3));
        passportDto.setBirthDate(LocalDate.of(2000, 5, 15));
        passportDto.setIssueDate(LocalDate.of(2000, 5, 15));
        passportDto.setPassportNumber(DataGenerator.generatePassportNumber());
        passportDto.setIssuer(DataGenerator.generateRandomString(10));
        passportDto.setIssuerNumber(DataGenerator.generatePassportIssuerNumber());

        return passportDto;
    }
}