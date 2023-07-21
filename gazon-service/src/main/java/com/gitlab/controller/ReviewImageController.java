package com.gitlab.controller;

import com.gitlab.controller.api.ReviewImageRestApi;
import com.gitlab.dto.ReviewImageDto;
import com.gitlab.mapper.ReviewImageMapper;
import com.gitlab.model.ReviewImage;
import com.gitlab.service.ReviewImageService;
import com.gitlab.util.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ReviewImageController implements ReviewImageRestApi {

    private final ReviewImageService reviewImageService;
    private final ReviewImageMapper reviewImageMapper;

    @Override
    public ResponseEntity<long[]> getAll() {
        var reviewImages = reviewImageService.findAll();
        return reviewImages.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(reviewImages.stream()
                        .map(ReviewImage::getId).mapToLong(Long::valueOf).toArray());
    }

    @Override
    public ResponseEntity<?> get(Long id) {
        Optional<ReviewImage> reviewImage = reviewImageService.findById(id);

        if (reviewImage.isEmpty()) return ResponseEntity.notFound().build();

        if (reviewImage.get().getData().length < 60) return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .contentType(MediaType.APPLICATION_JSON).body(reviewImageMapper.toDto(reviewImage.get()));

        return reviewImage.map(image -> ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.parseMediaType(MediaType.IMAGE_JPEG_VALUE))
                .body(ImageUtils.decompressImage(image.getData()))).orElse(null);

    }

    @Override
    public ResponseEntity<ReviewImageDto> update(MultipartFile file, Long id) throws IOException {
        Optional<ReviewImage> reviewImage = reviewImageService.findById(id);

        if (reviewImage.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        ReviewImage imageToBeUpdated = new ReviewImage();
        imageToBeUpdated.setName(file.getOriginalFilename());
        imageToBeUpdated.setData(ImageUtils.compressImage(file.getBytes()));

        return ResponseEntity.status(HttpStatus.OK)
                .body(ImageUtils.reviewImageDtoDecompressed(reviewImageMapper
                        .toDto(reviewImageService
                                .update(id, imageToBeUpdated).orElse(null))));

    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        Optional<ReviewImage> reviewImage = reviewImageService.delete(id);

        return reviewImage.isEmpty() ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok().build();
    }
}