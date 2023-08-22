package com.gitlab.controller;

import com.gitlab.controller.api.ReviewImageRestApi;
import com.gitlab.dto.ReviewImageDto;
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
        Optional<ReviewImageDto> reviewImageDto = reviewImageService.findByIdDto(id);

        if (reviewImageDto.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ReviewImageDto dto = reviewImageDto.get();

        if (dto.getData().length < 60) {
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(dto);
        }

        byte[] decompressedData = ImageUtils.decompressImage(dto.getData());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(MediaType.IMAGE_JPEG_VALUE))
                .body(decompressedData);
    }

    @Override
    public ResponseEntity<ReviewImageDto> update(MultipartFile file, Long id) throws IOException {
        Optional<ReviewImageDto> reviewImageDto = reviewImageService.findByIdDto(id);

        if (reviewImageDto.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        var dtoToBeUpdated = new ReviewImageDto();
        dtoToBeUpdated.setName(file.getOriginalFilename());
        dtoToBeUpdated.setData(ImageUtils.compressImage(file.getBytes()));

        Optional<ReviewImageDto> updatedDto = reviewImageService.updateDto(id, dtoToBeUpdated);

        if (updatedDto.isPresent()) {
            ReviewImageDto decompressedDto = ImageUtils.reviewImageDtoDecompressed(updatedDto.get());
            return ResponseEntity.status(HttpStatus.OK).body(decompressedDto);
        }

        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        Optional<ReviewImage> reviewImage = reviewImageService.delete(id);

        return reviewImage.isEmpty() ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok().build();
    }
}