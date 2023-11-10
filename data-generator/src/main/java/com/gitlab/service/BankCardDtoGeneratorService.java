package com.gitlab.service;

import com.gitlab.dto.BankCardDto;
import com.gitlab.util.DataGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class BankCardDtoGeneratorService {
    public BankCardDto generateBankCardDto() {
        BankCardDto bankCardDto = new BankCardDto();

        String cardNumber = DataGenerator.generateRandomString(8);
        bankCardDto.setCardNumber(cardNumber);

        bankCardDto.setDueDate(LocalDate.of(1900, 1, 1));

        int securityCode = Integer.parseInt(DataGenerator.generateRandomString(3));
        bankCardDto.setSecurityCode(securityCode);

        return bankCardDto;
    }
}
