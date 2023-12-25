package com.gitlab.controller;

import com.gitlab.controllers.api.rest.ProductImageRestApi;
import com.gitlab.dto.ProductImageDto;
import com.gitlab.dto.ReviewImageDto;
import com.gitlab.mapper.ProductImageMapper;
import com.gitlab.model.ProductImage;
import com.gitlab.service.ProductImageService;
import com.gitlab.util.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ProductImageController implements ProductImageRestApi {

    private final ProductImageService productImageService;
    private final ProductImageMapper productImageMapper;

    @Override
    public ResponseEntity<Page<ProductImageDto>> getPage(Integer page, Integer size) {
        if (page == null || size == null) {
            return createUnPagedResponse();
        }
        if (page < 0 || size < 1) {
            return ResponseEntity.noContent().build();
        }
        var productImagePage = productImageService.getPage(page, size);
        if (productImagePage.getContent().isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return createPagedResponse(productImagePage);
        }
    }
    private ResponseEntity<Page<ProductImageDto>> createPagedResponse(Page<ProductImage> productImagePage) {
        var productImageDtoPage = productImageService.getPageDto(productImagePage.getPageable().getPageNumber(),
                productImagePage.getPageable().getPageSize());
        return ResponseEntity.ok(productImageDtoPage);
    }

    private ResponseEntity<Page<ProductImageDto>> createUnPagedResponse() {
        var productImageDtos = productImageService.findAllDto();
        if (productImageDtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(new PageImpl<>(productImageDtos));
    }


    @Override
    public ResponseEntity<?> get(@PathVariable Long id) {
        Optional<ProductImage> productImage = productImageService.findById(id);
        if (productImage.isEmpty())
            return ResponseEntity.notFound().build();

        if (productImage.get().getData().length < 60)
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(productImageMapper.toDto(productImage.get()));

        return productImage.map(image ->
                ResponseEntity.status(HttpStatus.OK)
                        .contentType(MediaType.parseMediaType(MediaType.IMAGE_JPEG_VALUE))
                        .body(ImageUtils.decompressImage(image.getData()))).orElse(null);
    }

    @Override
    public ResponseEntity<List<ProductImageDto>> getAllByProduct(Long id) {
        List<ProductImageDto> productImageDtos = productImageService.findAllByProductIdDto(id);
        return productImageDtos.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(productImageDtos);
    }

    @Override
    public ResponseEntity<ProductImageDto> create(ProductImageDto productImageDto) {
        ProductImageDto saveProductImageDto = productImageService.saveDto(productImageDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(saveProductImageDto);
    }

    @Override
    public ResponseEntity<ProductImageDto> update(Long id, ProductImageDto productImageDto) {
        Optional<ProductImageDto> updatedProductImageDto = productImageService.updateDto(id, productImageDto);
        return updatedProductImageDto
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<ProductImage> productImage = productImageService.delete(id);

        return productImage.isEmpty() ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok().build();
    }
}