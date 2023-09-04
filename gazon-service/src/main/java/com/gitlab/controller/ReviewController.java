package com.gitlab.controller;

import com.gitlab.controller.api.ReviewRestApi;
import com.gitlab.dto.ReviewDto;
import com.gitlab.model.Review;
import com.gitlab.model.ReviewImage;
import com.gitlab.service.ReviewImageService;
import com.gitlab.service.ReviewService;
import com.gitlab.util.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ReviewController implements ReviewRestApi {

    private final ReviewService reviewService;

    private final ReviewImageService reviewImageService;

    @Override
    public ResponseEntity<List<ReviewDto>> getAll() {
        List<ReviewDto> reviewDtos = reviewService.findAllDto();
        return reviewDtos.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(reviewDtos);
    }

    @Override
    public ResponseEntity<ReviewDto> get(Long id) {
        Optional<ReviewDto> reviewDtoOptional = reviewService.findByIdDto(id);

        return reviewDtoOptional.map(reviewDto ->
                ResponseEntity.ok(reviewDto)).orElse(ResponseEntity.notFound().build());
    }


    @Override
    public ResponseEntity<ReviewDto> create(ReviewDto reviewDto) {
        ReviewDto createdReviewDto = reviewService.saveDto(reviewDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReviewDto);
    }


    @Override
    public ResponseEntity<ReviewDto> update(Long id, ReviewDto reviewDto) {
        Optional<ReviewDto> updatedReviewDtoOptional = reviewService.updateDto(id, reviewDto);

        return updatedReviewDtoOptional.map(updatedReviewDto ->
                ResponseEntity.ok(updatedReviewDto)).orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        Optional<Review> reviewOptional = reviewService.delete(id);
        return reviewOptional.isEmpty() ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<long[]> getImagesIDsByReviewId(Long id) {
        Optional<Review> reviewOptional = reviewService.findById(id);

        if (reviewOptional.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        if (reviewOptional.get().getReviewImages().isEmpty()) return ResponseEntity
                .status(HttpStatus.NO_CONTENT).build();

        long[] images = reviewOptional.orElse(null).getReviewImages().stream()
                .map(ReviewImage::getId).mapToLong(Long::valueOf).toArray();
        return ResponseEntity.status(HttpStatus.OK)
                .body(images);
    }

    @Override
    public ResponseEntity<String> uploadImagesByReviewId(MultipartFile[] files, Long id) throws IOException {
        Optional<Review> reviewOptional = reviewService.findById(id);

        if (reviewOptional.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("There is no reviewOptional with such id");
        if (files.length == 0) return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("At least one file should be included");

        List<ReviewImage> imageList = new ArrayList<>();
        for (MultipartFile file : files) {
            var image = new ReviewImage();
            image.setReview(reviewOptional.get());
            image.setName(file.getOriginalFilename());
            image.setData(ImageUtils.compressImage(file.getBytes()));
            imageList.add(image);
        }
        reviewImageService.saveAll(imageList);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<String> deleteAllImagesByReviewId(Long id) {
        Optional<Review> reviewOptional = reviewService.findById(id);

        if (reviewOptional.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("There is no reviewOptional with such id");
        if (reviewOptional.get().getReviewImages().isEmpty()) return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body("reviewOptional with such id has no images");

        reviewOptional.get().getReviewImages().stream().map(ReviewImage::getId).forEach(reviewImageService::delete);

        return ResponseEntity.ok().build();

    }
}