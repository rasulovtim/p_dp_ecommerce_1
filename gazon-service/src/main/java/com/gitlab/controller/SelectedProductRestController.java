package com.gitlab.controller;

import com.gitlab.controller.api.SelectedProductRestAPI;
import com.gitlab.dto.SelectedProductDto;
import com.gitlab.mapper.SelectedProductMapper;
import com.gitlab.model.SelectedProduct;
import com.gitlab.service.SelectedProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class SelectedProductRestController implements SelectedProductRestAPI {


    private final SelectedProductService selectedProductService;
    private final SelectedProductMapper selectedProductMapper;


    @Override
    public ResponseEntity<List<SelectedProductDto>> getAll() {
        var selectedProducts = selectedProductService.findAll();
        if (selectedProducts.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(selectedProducts.stream().map(selectedProductMapper::toDto).toList());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<SelectedProductDto> get(@PathVariable Long id) {
        Optional<SelectedProduct> productOptional = selectedProductService.findById(id);

        return productOptional.map(selectedProductMapper::toDto).map(productDto -> ResponseEntity.status(HttpStatus.OK)
                .body(productDto)).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

    }


    @Override
    public ResponseEntity<SelectedProductDto> create(SelectedProductDto selectedProductDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(selectedProductMapper
                        .toDto(selectedProductService
                                .save(selectedProductMapper
                                        .toEntity(selectedProductDto))));
    }

    @Override
    public ResponseEntity<SelectedProductDto> update(Long id, SelectedProductDto selectedProductDto) {
        return selectedProductService.update(id, selectedProductMapper.toEntity(selectedProductDto))
                .map(product -> ResponseEntity.ok(selectedProductMapper.toDto(product)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        Optional<SelectedProduct> selectedProduct = selectedProductService.delete(id);
        if (selectedProduct.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().build();
        }
    }
}
