package com.gitlab.controller;

import com.gitlab.controller.api.StoreRestApi;
import com.gitlab.dto.StoreDto;
import com.gitlab.mapper.StoreMapper;
import com.gitlab.model.Store;
import com.gitlab.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class StoreRestController implements StoreRestApi {
    
    private final StoreService storeService;
    private final StoreMapper storeMapper;

    @Override
    public ResponseEntity<List<StoreDto>> getAll() {
        var store = storeService.findAll();

        if(store.isEmpty()){
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.ok(store.stream().map(storeMapper::toDto).toList());
        }
    }

    @Override
    public ResponseEntity<StoreDto> get(Long id) {
        Optional<Store> productOptional = storeService.findById(id);

        return productOptional.map(storeMapper::toDto)
                .map(productDto -> ResponseEntity.status(HttpStatus.OK).body(productDto))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Override
    public ResponseEntity<StoreDto> create(StoreDto storeDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(storeMapper
                        .toDto(storeService
                                .save(storeMapper
                                        .toEntity(storeDto))));
    }

    @Override
    public ResponseEntity<StoreDto> update(Long id, StoreDto storeDto) {
        return storeService.update(id, storeMapper.toEntity(storeDto))
                .map(store -> ResponseEntity.ok(storeMapper.toDto(store)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        Optional<Store> product = storeService.delete(id);
        return product.isEmpty() ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok().build();
    }
}
