package com.gitlab.service;

import com.gitlab.repository.BankCardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class BankCardServiceTest {

    @Mock
    private BankCardRepository bankCardRepository;
    @InjectMocks
    private BankCardService bankCardService;

    @Test
    void should_find_all_bankCards() {
        List<BankCard> expectedResult = generateBankCards();
        when(bankCardRepository.findAll()).thenReturn(generateBankCards());

        List<BankCard> actualResult = bankCardService.findAll();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void should_find_bankCard_by_id() {
        long id = 1L;
        BankCard expectedResult = generateBankCard();
        when(bankCardRepository.findById(id)).thenReturn(Optional.of(expectedResult));

        Optional<BankCard> actualResult = bankCardService.findById(id);

        assertEquals(expectedResult, actualResult.orElse(null));
    }

    @Test
    void should_save_bankCard() {
        BankCard expectedResult = generateBankCard();
        when(bankCardRepository.save(expectedResult)).thenReturn(expectedResult);

        BankCard actualResult = bankCardService.save(expectedResult);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void should_update_bankCard() {
        long id = 1L;
        BankCard bankCardToUpdate = new BankCard();
        bankCardToUpdate.setCardNumber(/*modified*/"12345678"/*modified*/);
        bankCardToUpdate.setDueDate(LocalDate.MIN);
        bankCardToUpdate.setSecurityCode(123);

        BankCard bankCardBeforeUpdate = new BankCard(id, /*unmodified*/"23456789"/*unmodified*/, LocalDate.MIN, 123);
        BankCard updatedBankCard = new BankCard(id, /*modified*/"12345678"/*modified*/, LocalDate.MIN, 123);

        when(bankCardRepository.findById(id)).thenReturn(Optional.of(bankCardBeforeUpdate));
        when(bankCardRepository.save(updatedBankCard)).thenReturn(updatedBankCard);

        Optional<BankCard> actualResult = bankCardService.update(id, bankCardToUpdate);

        assertEquals(updatedBankCard, actualResult.orElse(null));
    }

    @Test
    void should_not_update_bankCard_when_entity_not_found() {
        long id = 1L;
        BankCard bankCardToUpdate = new BankCard();
        bankCardToUpdate.setCardNumber(/*modified*/"12345678"/*modified*/);

        when(bankCardRepository.findById(id)).thenReturn(Optional.empty());

        Optional<BankCard> actualResult = bankCardService.update(id, bankCardToUpdate);

        verify(bankCardRepository, never()).save(any());
        assertNull(actualResult.orElse(null));
    }

    @Test
    void should_delete_bankCard() {
        long id = 1L;
        when(bankCardRepository.findById(id)).thenReturn(Optional.of(generateBankCard()));

        bankCardService.delete(id);

        verify(bankCardRepository).deleteById(id);
    }

    @Test
    void should_not_delete_bankCard_when_entity_not_found() {
        long id = 1L;
        when(bankCardRepository.findById(id)).thenReturn(Optional.empty());

        bankCardService.delete(id);

        verify(bankCardRepository, never()).deleteById(anyLong());
    }

    private List<BankCard> generateBankCards() {
        return List.of(
                new BankCard(1L, "cardNumber1", LocalDate.MIN, 123),
                new BankCard(2L, "cardNumber2", LocalDate.MIN, 123),
                new BankCard(3L, "cardNumber3", LocalDate.MIN, 123),
                new BankCard(4L, "cardNumber4", LocalDate.MIN, 123),
                new BankCard(5L, "cardNumber5", LocalDate.MIN, 123));
    }

    private BankCard generateBankCard() {
        return new BankCard(1L, "cardNumber1", LocalDate.MIN, 123);
    }

    @Test
    void should_not_updated_cardNumber_field_if_null() {
        long id = 1L;
        BankCard bankCardToUpdate = new BankCard();
        bankCardToUpdate.setCardNumber(null);

        BankCard bankCardBeforeUpdate = new BankCard(id, /*unmodified*/"12345678"/*unmodified*/, LocalDate.MIN, 123);

        when(bankCardRepository.findById(id)).thenReturn(Optional.of(bankCardBeforeUpdate));
        when(bankCardRepository.save(bankCardBeforeUpdate)).thenReturn(bankCardBeforeUpdate);

        Optional<BankCard> actualResult = bankCardService.update(id, bankCardToUpdate);

        verify(bankCardRepository).save(bankCardBeforeUpdate);
        assertEquals(bankCardBeforeUpdate, actualResult.orElse(null));
    }

    @Test
    void should_not_updated_dueDate_field_if_null() {
        long id = 1L;
        BankCard bankCardToUpdate = new BankCard();
        bankCardToUpdate.setDueDate(null);

        BankCard bankCardBeforeUpdate = new BankCard(id, "cardNumber", /*Unmodified*/ LocalDate.MIN /*Unmodified*/, 123);

        when(bankCardRepository.findById(id)).thenReturn(Optional.of(bankCardBeforeUpdate));
        when(bankCardRepository.save(bankCardBeforeUpdate)).thenReturn(bankCardBeforeUpdate);

        Optional<BankCard> actualResult = bankCardService.update(id, bankCardToUpdate);

        verify(bankCardRepository).save(bankCardBeforeUpdate);
        assertEquals(bankCardBeforeUpdate, actualResult.orElse(null));
    }

    @Test
    void should_not_updated_securityCode_field_if_null() {
        long id = 1L;
        BankCard bankCardToUpdate = new BankCard();
        bankCardToUpdate.setSecurityCode(null);

        BankCard bankCardBeforeUpdate = new BankCard(id, "cardNumber", LocalDate.MIN, /*Unmodified*/123/*Unmodified*/);

        when(bankCardRepository.findById(id)).thenReturn(Optional.of(bankCardBeforeUpdate));
        when(bankCardRepository.save(bankCardBeforeUpdate)).thenReturn(bankCardBeforeUpdate);

        Optional<BankCard> actualResult = bankCardService.update(id, bankCardToUpdate);

        verify(bankCardRepository).save(bankCardBeforeUpdate);
        assertEquals(bankCardBeforeUpdate, actualResult.orElse(null));
    }

}
