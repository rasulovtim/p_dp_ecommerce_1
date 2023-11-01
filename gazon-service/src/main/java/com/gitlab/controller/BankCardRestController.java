package com.gitlab.controller;

import com.gitlab.controllers.api.rest.BankCardRestApi;
import com.gitlab.dto.BankCardDto;
import com.gitlab.service.BankCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Validated
@RestController
@RequiredArgsConstructor
public class BankCardRestController implements BankCardRestApi {

    private final BankCardService bankCardService;

    public ResponseEntity<List<BankCardDto>> getAll() {
        List<BankCardDto> bankCardDtos = bankCardService.findAllDto();

        if (bankCardDtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(bankCardDtos);
        }
    }

    @Override
    public ResponseEntity<BankCardDto> get(Long id) {
        Optional<BankCardDto> optionalBankCardDto = bankCardService.findByIdDto(id);

        return optionalBankCardDto
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<BankCardDto> create(BankCardDto bankCardDto) {
        BankCardDto savedBankCardDto = bankCardService.saveDto(bankCardDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedBankCardDto);
    }

    @Override
    public ResponseEntity<BankCardDto> update(Long id, BankCardDto bankCardDto) {
        Optional<BankCardDto> updatedBankCardDto = bankCardService.updateDto(id, bankCardDto);

        return updatedBankCardDto
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        if (bankCardService.delete(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
