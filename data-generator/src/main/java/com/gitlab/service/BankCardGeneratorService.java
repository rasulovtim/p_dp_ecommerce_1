package com.gitlab.service;

import com.gitlab.dto.BankCardDto;
import com.gitlab.util.DataGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class BankCardGeneratorService {

    public BankCardDto generateBankCard() {
        BankCardDto bankCardDto = new BankCardDto();

        String cardNumber = DataGenerator.generateRandomNumericString(8);
        bankCardDto.setCardNumber(cardNumber);

        bankCardDto.setDueDate(LocalDate.of(1900, 1, 1));

        int securityCode = Integer.parseInt(DataGenerator.generateRandomNumericString(3));
        bankCardDto.setSecurityCode(securityCode);

        return bankCardDto;
    }
}