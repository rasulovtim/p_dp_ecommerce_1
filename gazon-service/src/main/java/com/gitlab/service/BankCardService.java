package com.gitlab.service;

import com.gitlab.dto.BankCardDto;
import com.gitlab.mapper.BankCardMapper;
import com.gitlab.model.BankCard;
import com.gitlab.repository.BankCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BankCardService {
    private final BankCardRepository bankCardRepository;

    private final BankCardMapper bankCardMapper;

    public List<BankCard> findAll() {
        return bankCardRepository.findAll();
    }

    public List<BankCardDto> findAllDto() {
        List<BankCard> bankCards = bankCardRepository.findAll();
        return bankCards.stream()
                .map(bankCardMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<BankCard> findById(Long id) {
        return bankCardRepository.findById(id);
    }

    public Optional<BankCardDto> findByIdDto(Long id) {
        return bankCardRepository.findById(id)
                .map(bankCardMapper::toDto);
    }

    public BankCard save(BankCard bankCard) {
        return bankCardRepository.save(bankCard);
    }

    public BankCardDto saveDto(BankCardDto bankCardDto) {
        BankCard bankCard = bankCardMapper.toEntity(bankCardDto);
        BankCard savedBankCard = bankCardRepository.save(bankCard);
        return bankCardMapper.toDto(savedBankCard);
    }

    public Optional<BankCard> update(Long id, BankCard bankCard) {
        Optional<BankCard> optionalSavedCard = findById(id);
        BankCard savedCard;
        if (optionalSavedCard.isEmpty()) {
            return optionalSavedCard;
        } else {
            savedCard = optionalSavedCard.get();
        }
        if (bankCard.getCardNumber() != null) {
            savedCard.setCardNumber(bankCard.getCardNumber());
        }
        if (bankCard.getDueDate() != null) {
            savedCard.setDueDate(bankCard.getDueDate());
        }
        if (bankCard.getSecurityCode() != null) {
            savedCard.setSecurityCode(bankCard.getSecurityCode());
        }
        return Optional.of(bankCardRepository.save(savedCard));
    }

    public Optional<BankCardDto> updateDto(Long id, BankCardDto bankCardDto) {
        Optional<BankCard> optionalSavedCard = findById(id);
        if (optionalSavedCard.isEmpty()) {
            return Optional.empty();
        } else {
            BankCard savedCard = optionalSavedCard.get();

            if (bankCardDto.getCardNumber() != null) {
                savedCard.setCardNumber(bankCardDto.getCardNumber());
            }
            if (bankCardDto.getDueDate() != null) {
                savedCard.setDueDate(bankCardDto.getDueDate());
            }
            if (bankCardDto.getSecurityCode() != null) {
                savedCard.setSecurityCode(bankCardDto.getSecurityCode());
            }

            BankCard updatedCard = bankCardRepository.save(savedCard);
            return Optional.ofNullable(bankCardMapper.toDto(updatedCard));
        }
    }

    public boolean delete(Long id) {
        Optional<BankCard> optionalSavedCard = findById(id);
        if (optionalSavedCard.isPresent()) {
            bankCardRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public Optional<BankCardDto> deleteDto(Long id) {
        Optional<BankCard> optionalSavedCard = findById(id);
        if (optionalSavedCard.isPresent()) {
            BankCardDto deletedDto = bankCardMapper.toDto(optionalSavedCard.get());
            bankCardRepository.deleteById(id);
            return Optional.of(deletedDto);
        } else {
            return Optional.empty();
        }
    }

}