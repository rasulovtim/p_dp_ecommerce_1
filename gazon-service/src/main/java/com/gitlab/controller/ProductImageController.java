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
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ProductImageController implements ProductImageRestApi {

    private final ProductImageService productImageService;
    private final ProductImageMapper productImageMapper;

    @Override
    public ResponseEntity<long[]> getAll() {
        var productImages = productImageService.findAll();
        return productImages.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(productImages.stream()
                        .map(ProductImage::getId).mapToLong(Long::valueOf).toArray());
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
    public ResponseEntity<ProductImageDto> update(MultipartFile file, @PathVariable Long id) throws IOException {
        Optional<ProductImage> productImage = productImageService.findById(id);

        if (productImage.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        var imageToBeUpdated = new ProductImage();
        imageToBeUpdated.setName(file.getOriginalFilename());
        imageToBeUpdated.setData(ImageUtils.compressImage(file.getBytes()));

        return ResponseEntity.status(HttpStatus.OK)
                .body(ImageUtils.decompressAndReturnDto(productImageMapper
                        .toDto(productImageService
                                .update(id, imageToBeUpdated).orElse(null))));
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<ProductImage> productImage = productImageService.delete(id);

        return productImage.isEmpty() ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok().build();
    }
}