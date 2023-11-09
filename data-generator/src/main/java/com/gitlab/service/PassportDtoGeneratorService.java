package com.gitlab.service;

import com.gitlab.client.PassportDtoGeneratorClient;
import com.gitlab.dto.PassportDto;
import com.gitlab.util.DataGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PassportDtoGeneratorService {
    private final PassportDtoGeneratorClient passportDtoGeneratorClient;

    public PassportDto generatePassportDto() {
        String additionalData = DataGenerator.generateRandomSequence(3);
        PassportDto passportDto = DataGenerator.generatePassportDtoData(additionalData);

        passportDtoGeneratorClient.create(passportDto);
        return passportDto;
    }

}
