package com.gitlab.controller;

import com.gitlab.controllers.api.rest.ReviewImageRestApi;
import com.gitlab.dto.ReviewImageDto;
import com.gitlab.model.ReviewImage;
import com.gitlab.service.ReviewImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
public class ReviewImageController implements ReviewImageRestApi {

    private final ReviewImageService reviewImageService;

    public ResponseEntity<List<ReviewImageDto>> getPage(Integer page, Integer size) {
        var reviewImagePage = reviewImageService.getPageDto(page, size);
        if (reviewImagePage == null || reviewImagePage.getContent().isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reviewImagePage.getContent());
    }

    @Override
    public ResponseEntity<ReviewImageDto> get(Long id) {
        return reviewImageService.findByIdDto(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<ReviewImageDto> create(ReviewImageDto reviewImageDto) {
        ReviewImageDto savedReviewImageDto = reviewImageService.saveDto(reviewImageDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedReviewImageDto);
    }

    @Override
    public ResponseEntity<ReviewImageDto> update(Long id, ReviewImageDto reviewImageDto) {
        Optional<ReviewImageDto> updatedReviewImageDto = reviewImageService.updateDto(id, reviewImageDto);
        return updatedReviewImageDto
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        Optional<ReviewImage> reviewImage = reviewImageService.delete(id);

        if (reviewImage.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().build();
        }
    }
}