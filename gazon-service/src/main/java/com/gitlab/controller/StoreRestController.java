package com.gitlab.controller;

import com.gitlab.controllers.api.rest.StoreRestApi;
import com.gitlab.dto.StoreDto;
import com.gitlab.dto.StoreDto;
import com.gitlab.model.Store;
import com.gitlab.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class StoreRestController implements StoreRestApi {

    private final StoreService storeService;

    public ResponseEntity<List<StoreDto>> getPage(Integer page, Integer size) {
        var storePage = storeService.getPageDto(page, size);
        if (storePage == null || storePage.getContent().isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(storePage.getContent());
    }

    @Override
    public ResponseEntity<StoreDto> get(Long id) {
        return storeService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<StoreDto> create(StoreDto storeDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(storeService
                                .save(storeDto));
    }

    @Override
    public ResponseEntity<StoreDto> update(Long id, StoreDto storeDto) {
        Optional<StoreDto> updatedStoreDto = Optional.ofNullable(storeService.update(id, storeDto));
        return updatedStoreDto
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        Optional<StoreDto> product = Optional.ofNullable(storeService.delete(id));
        return product.isEmpty() ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok().build();
    }
}