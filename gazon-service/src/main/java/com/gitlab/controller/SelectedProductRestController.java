package com.gitlab.controller;

import com.gitlab.controller.api.SelectedProductRestAPI;
import com.gitlab.dto.SelectedProductDto;
import com.gitlab.mapper.SelectedProductMapper;
import com.gitlab.model.SelectedProduct;
import com.gitlab.service.SelectedProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
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

        return selectedProducts.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(selectedProducts.stream().map(selectedProductMapper::toDto).toList());
    }

    @Override
    public ResponseEntity<SelectedProductDto> get(Long id, boolean no_sum_no_weight) {
        Optional<SelectedProduct> productOptional = selectedProductService.findById(id);

        if (productOptional.isEmpty()) return ResponseEntity.notFound().build();

        SelectedProduct selectedProduct = productOptional.orElse(null);
        SelectedProductDto selectedProductDto = selectedProductMapper.toDto(selectedProduct);

        if (no_sum_no_weight) return ResponseEntity.status(HttpStatus.OK).body(selectedProductDto);

        selectedProductDto
                .setSum(selectedProduct.getProduct().getPrice()
                        .multiply(BigDecimal.valueOf(selectedProduct.getCount())));
        selectedProductDto
                .setTotalWeight(selectedProduct.getProduct().getWeight() * selectedProduct.getCount());

        return ResponseEntity.status(HttpStatus.OK).body(selectedProductDto);
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
        return selectedProduct.isEmpty() ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok().build();

    }
}