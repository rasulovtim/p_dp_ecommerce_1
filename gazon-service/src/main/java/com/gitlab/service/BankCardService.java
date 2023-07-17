package com.gitlab.service;

import com.gitlab.repository.BankCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BankCardService {

    private final BankCardRepository bankCardRepository;


    public List<BankCard> findAll () {
        return bankCardRepository.findAll();
    }

    public Optional<BankCard> findById (Long id) {
        return bankCardRepository.findById(id);
    }

    public BankCard save(BankCard bankCard) {
        return bankCardRepository.save(bankCard);
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

    public Optional<BankCard> delete(Long id) {
        Optional<BankCard> optionalSavedCard = findById(id);
        if (optionalSavedCard.isEmpty()) {
            return optionalSavedCard;
        } else {
            bankCardRepository.deleteById(id);
            return optionalSavedCard;
        }
    }

}
