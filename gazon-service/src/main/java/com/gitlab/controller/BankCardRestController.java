package com.gitlab.controller;

import com.gitlab.controller.api.BankCardRestApi;
import com.gitlab.dto.BankCardDto;
import com.gitlab.mapper.BankCardMapper;
import com.gitlab.model.BankCard;
import com.gitlab.service.BankCardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
public class BankCardRestController implements BankCardRestApi {

    private final BankCardService bankCardService;
    private final BankCardMapper bankCardMapper;

    @Override
    public ResponseEntity<List<BankCardDto>> getAll() {
        var bankCards = bankCardService.findAll();
        if (bankCards.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(bankCards.stream().map(bankCardMapper::toDto).toList());
        }
    }



    @Override
    public ResponseEntity<BankCardDto> get(Long id) {
        return bankCardService.findById(id)
                .map(value -> ResponseEntity.ok(bankCardMapper.toDto(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<BankCardDto> create(BankCardDto bankCardDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bankCardMapper
                        .toDto(bankCardService
                                .save(bankCardMapper
                                        .toEntity(bankCardDto))));
    }

    @Override
    public ResponseEntity<BankCardDto> update(Long id, BankCardDto bankCardDto) {
        return bankCardService.update(id, bankCardMapper.toEntity(bankCardDto))
                .map(bankCard -> ResponseEntity.ok(bankCardMapper.toDto(bankCard)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        Optional<BankCard> bankCard = bankCardService.delete(id);
        if (bankCard.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().build();
        }
    }
}
