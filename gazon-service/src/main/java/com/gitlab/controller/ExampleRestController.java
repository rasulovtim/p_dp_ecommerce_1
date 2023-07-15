package com.gitlab.controller;

import com.gitlab.controller.api.ExampleRestApi;
import com.gitlab.dto.ExampleDto;
import com.gitlab.mapper.ExampleMapper;
import com.gitlab.model.Example;
import com.gitlab.service.ExampleService;
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
public class ExampleRestController implements ExampleRestApi {

    private final ExampleService exampleService;
    private final ExampleMapper exampleMapper;

    @Override
    public ResponseEntity<Page<ExampleDto>> getPage(Integer page, Integer size) {
        if (page == null || size == null) {
            var examples = exampleService.findAll();
            if (examples.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(new PageImpl<>(examples.stream().map(exampleMapper::toDto).toList()));
        }

        if (page < 0 || size < 1) {
            return ResponseEntity.noContent().build();
        }

        var examplePage = exampleService.getPage(page, size);
        if (examplePage.getContent().isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            var exampleDtoPage = new PageImpl<>(
                    examplePage.getContent().stream().map(exampleMapper::toDto).toList(),
                    examplePage.getPageable(),
                    examplePage.getTotalElements()
            );
            return ResponseEntity.ok(exampleDtoPage);
        }
    }

    @Override
    public ResponseEntity<ExampleDto> get(Long id) {
        return exampleService.findById(id)
                .map(value -> ResponseEntity.ok(exampleMapper.toDto(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<ExampleDto> create(ExampleDto exampleDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(exampleMapper
                        .toDto(exampleService
                                .save(exampleMapper
                                        .toEntity(exampleDto))));
    }

    @Override
    public ResponseEntity<ExampleDto> update(Long id, ExampleDto exampleDto) {
        return exampleService.update(id, exampleMapper.toEntity(exampleDto))
                .map(example -> ResponseEntity.ok(exampleMapper.toDto(example)))
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