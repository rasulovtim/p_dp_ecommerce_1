package com.gitlab.controller;

import com.gitlab.controller.api.ProductImageRestApi;
import com.gitlab.dto.ProductImageDto;
import com.gitlab.mapper.ProductImageMapper;
import com.gitlab.model.ProductImage;
import com.gitlab.service.ProductImageService;
import com.gitlab.util.ImageUtils;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<List<ProductImageDto>> getAll() {
        var productImages = productImageService.findAll();
        if (productImages.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(productImages.stream().map(productImageMapper::toDto).toList());
        }
    }

    @Override
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        Optional<ProductImage> productImage = productImageService.findById(id);

        if (productImage.isEmpty()) return ResponseEntity.notFound().build();

        if (productImage.get().getData().length < 60) return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .contentType(MediaType.APPLICATION_JSON).body(productImageMapper.toDto(productImage.get()));

        return productImage.map(image -> ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.parseMediaType(MediaType.IMAGE_JPEG_VALUE))
                .body(ImageUtils.decompressImage(image.getData()))).orElse(null);

    }

    @Override
    public ResponseEntity<ProductImageDto> update(MultipartFile file, Long id) throws IOException {
        Optional<ProductImage> productImage = productImageService.findById(id);

        if (productImage.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        ProductImage imageToBeUpdated = new ProductImage();
        imageToBeUpdated.setName(file.getOriginalFilename());
        imageToBeUpdated.setData(ImageUtils.compressImage(file.getBytes()));

        return ResponseEntity.status(HttpStatus.OK)
                .body(ImageUtils.decompressAndReturnDto(productImageMapper
                        .toDto(productImageService
                                .update(id, imageToBeUpdated).orElse(null))));

    }


    @Override
    public ResponseEntity<Void> delete(Long id) {
        Optional<ProductImage> productImage = productImageService.delete(id);

        if (productImage.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().build();
        }
    }

}
