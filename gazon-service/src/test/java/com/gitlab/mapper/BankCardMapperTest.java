package com.gitlab.mapper;

import com.gitlab.dto.BankCardDto;
import com.gitlab.model.BankCard;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BankCardMapperTest {

    private final BankCardMapper mapper = Mappers.getMapper(BankCardMapper.class);

    @Test
    void should_map_bankCard_to_Dto() {
        BankCard bankCard = getBankCard(1L);

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

    @Test
    void should_map_bankCardList_to_DtoList() {
        List<BankCard> bankCardList = List.of(getBankCard(1L), getBankCard(2L), getBankCard(3L));

        List<BankCardDto> bankCardDtoList = mapper.toDtoList(bankCardList);

        assertNotNull(bankCardDtoList);
        assertEquals(bankCardList.size(), bankCardList.size());
        for (int i = 0; i < bankCardDtoList.size(); i++) {
            BankCardDto dto = bankCardDtoList.get(i);
            BankCard entity = bankCardList.get(i);
            assertEquals(dto.getId(), entity.getId());
            assertEquals(dto.getCardNumber(), entity.getCardNumber());
            assertEquals(dto.getDueDate(), entity.getDueDate());
            assertEquals(dto.getSecurityCode(), entity.getSecurityCode());
        }
    }

    @Test
    void should_map_bankCardDtoList_to_EntityList() {
        List<BankCardDto> bankCardDtoList = List.of(getBankCardDto(1L), getBankCardDto(2L), getBankCardDto(3L));

        List<BankCard> bankCardList = mapper.toEntityList(bankCardDtoList);

        assertNotNull(bankCardList);
        assertEquals(bankCardList.size(), bankCardList.size());
        for (int i = 0; i < bankCardList.size(); i++) {
            BankCardDto dto = bankCardDtoList.get(i);
            BankCard entity = bankCardList.get(i);
            assertEquals(dto.getId(), entity.getId());
            assertEquals(dto.getCardNumber(), entity.getCardNumber());
            assertEquals(dto.getDueDate(), entity.getDueDate());
            assertEquals(dto.getSecurityCode(), entity.getSecurityCode());
        }
    }

    @NotNull
    private BankCard getBankCard(Long id) {
        BankCard bankCard = new BankCard();
        bankCard.setId(id);
        bankCard.setCardNumber("123456789" + id);
        bankCard.setDueDate(LocalDate.parse("2222-12-2" + id));
        bankCard.setSecurityCode((int) (22 + id));
        return bankCard;
    }

    @NotNull
    private BankCardDto getBankCardDto(Long id) {
        BankCardDto bankCardDto = new BankCardDto();
        bankCardDto.setId(id);
        bankCardDto.setCardNumber("123456789" + id);
        bankCardDto.setDueDate(LocalDate.parse("2222-12-2" + id));
        bankCardDto.setSecurityCode((int) (22 + id));
        return bankCardDto;
    }
}
