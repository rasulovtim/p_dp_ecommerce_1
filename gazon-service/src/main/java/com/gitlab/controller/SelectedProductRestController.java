package com.gitlab.controller;

import com.gitlab.controller.api.SelectedProductRestAPI;
import com.gitlab.dto.SelectedProductDto;
import com.gitlab.model.SelectedProduct;
import com.gitlab.service.SelectedProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class SelectedProductRestController implements SelectedProductRestAPI {

    private final SelectedProductService selectedProductService;

    @Override
    public ResponseEntity<List<SelectedProductDto>> getAll() {
        List<SelectedProductDto> selectedProductDtos = selectedProductService.findAllDto();

        return selectedProductDtos.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(selectedProductDtos);
    }

    @Override
    public ResponseEntity<SelectedProductDto> get(Long id) {
        Optional<SelectedProductDto> selectedProductOptional = selectedProductService.findByIdAndMapToDto(id);

        if (selectedProductOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        SelectedProductDto selectedProductDto = selectedProductOptional.get();
        double totalWeight = selectedProductService.calculateTotalWeight(selectedProductDto);
        selectedProductDto.setTotalWeight((long) totalWeight);

        return ResponseEntity.status(HttpStatus.OK).body(selectedProductDto);
    }

    @Override
    public ResponseEntity<SelectedProductDto> create(SelectedProductDto selectedProductDto) {
        SelectedProductDto savedSelectedProductDto = selectedProductService.saveDto(selectedProductDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSelectedProductDto);
    }

    @Override
    public ResponseEntity<SelectedProductDto> update(Long id, SelectedProductDto selectedProductDto) {
        Optional<SelectedProductDto> updatedProductDtoOptional = selectedProductService.updateSelectedProduct(id, selectedProductDto);

        return updatedProductDtoOptional
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        Optional<SelectedProduct> selectedProduct = selectedProductService.delete(id);
        return selectedProduct.isEmpty() ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok().build();

    }
}