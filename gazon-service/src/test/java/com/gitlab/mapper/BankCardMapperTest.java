package com.gitlab.mapper;

import com.gitlab.dto.BankCardDto;
import com.gitlab.model.BankCard;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BankCardMapperTest {

    private final BankCardMapper mapper = Mappers.getMapper(BankCardMapper.class);


    @Test
    void should_map_bankCard_to_Dto() {
        BankCard bankCard = new BankCard();
        bankCard.setId(1L);
        bankCard.setCardNumber("123456789");
        bankCard.setDueDate(LocalDate.parse("2222-12-22"));
        bankCard.setSecurityCode(222);

        BankCardDto actualResult = mapper.toDto(bankCard);

        assertNotNull(actualResult);
        assertEquals(bankCard.getId(), actualResult.getId());
        assertEquals(bankCard.getCardNumber(), actualResult.getCardNumber());
        assertEquals(bankCard.getDueDate(), actualResult.getDueDate());
        assertEquals(bankCard.getSecurityCode(), actualResult.getSecurityCode());
    }

    @Test
    void should_map_bankCardDto_to_Entity() {
        BankCardDto bankCardDto = new BankCardDto();
        bankCardDto.setId(1L);
        bankCardDto.setCardNumber("123456789");
        bankCardDto.setDueDate(LocalDate.parse("2024-12-24"));
        bankCardDto.setSecurityCode(2222);

        BankCard actualResult = mapper.toEntity(bankCardDto);

        assertNotNull(actualResult);
        assertEquals(bankCardDto.getId(), actualResult.getId());
        assertEquals(bankCardDto.getCardNumber(), actualResult.getCardNumber());
        assertEquals(bankCardDto.getDueDate(), actualResult.getDueDate());
        assertEquals(bankCardDto.getSecurityCode(), actualResult.getSecurityCode());
    }

}
