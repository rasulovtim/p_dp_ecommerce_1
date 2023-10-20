package com.gitlab.controller;

import com.gitlab.controller.api.PaymentRestApi;
import com.gitlab.controllers.api.rest.ExampleRestApi;
import com.gitlab.dto.ExampleDto;
import com.gitlab.dto.PaymentDto;
import com.gitlab.model.Example;
import com.gitlab.model.Payment;
import com.gitlab.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
public class PaymentRestController implements PaymentRestApi {

    private final PaymentService paymentService;

    @Override
    public ResponseEntity<Page<PaymentDto>> getPage(Integer page, Integer size) {
        if (page == null || size == null) {
            return createUnPagedResponse();
        }
        if (page < 0 || size < 1) {
            return ResponseEntity.noContent().build();
        }

        var paymentPage = paymentService.getPage(page, size);
        if (paymentPage.getContent().isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return createPagedResponse(paymentPage);
        }
    }

    private ResponseEntity<Page<ExampleDto>> createUnPagedResponse() {
        var exampleDtos = exampleService.findAllDto();
        if (exampleDtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(new PageImpl<>(exampleDtos));
    }

    private ResponseEntity<Page<ExampleDto>> createPagedResponse(Page<Payment> examplePage) {
        var exampleDtoPage = exampleService.getPageDto(examplePage.getPageable().getPageNumber(), examplePage.getPageable().getPageSize());
        return ResponseEntity.ok(exampleDtoPage);
    }

    @Override
    public ResponseEntity<ExampleDto> get(Long id) {
        return exampleService.findByIdDto(id)
                .map(value -> ResponseEntity.ok(value))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<ExampleDto> create(ExampleDto exampleDto) {
        ExampleDto savedExampleDto = exampleService.saveDto(exampleDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedExampleDto);
    }

    @Override
    public ResponseEntity<ExampleDto> update(Long id, ExampleDto exampleDto) {
        Optional<ExampleDto> updatedExampleDto = exampleService.updateDto(id, exampleDto);
        return updatedExampleDto
                .map(dto -> ResponseEntity.ok(dto))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        Optional<Example> example = exampleService.delete(id);
        if (example.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().build();
        }
    }
}