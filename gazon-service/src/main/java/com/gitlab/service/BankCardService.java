package com.gitlab.service;

import com.gitlab.dto.BankCardDto;
import com.gitlab.mapper.BankCardMapper;
import com.gitlab.model.BankCard;
import com.gitlab.repository.BankCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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

    public Page<BankCard> getPage(Integer page, Integer size) {

        if (page == null || size == null) {
            var bankCards = findAll();
            if (bankCards.isEmpty()) {
                return Page.empty();
            }
            return new PageImpl<>(bankCards);
        }
        if (page < 0 || size < 1) {
            return Page.empty();
        }
        PageRequest pageRequest = PageRequest.of(page, size);
        return bankCardRepository.findAll(pageRequest);
    }

    public Page<BankCardDto> getPageDto(Integer page, Integer size) {

        if (page == null || size == null) {
            var bankCards = findAllDto();
            if (bankCards.isEmpty()) {
                return Page.empty();
            }
            return new PageImpl<>(bankCards);
        }
        if (page < 0 || size < 1) {
            return Page.empty();
        }
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<BankCard> bankCardPage = bankCardRepository.findAll(pageRequest);
        return bankCardPage.map(bankCardMapper::toDto);
    }


    public BankCardDto saveDto(BankCardDto bankCardDto) {
        BankCard bankCard = bankCardMapper.toEntity(bankCardDto);
        BankCard savedBankCard = bankCardRepository.save(bankCard);
        return bankCardMapper.toDto(savedBankCard);
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