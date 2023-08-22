package com.gitlab.controller;

import com.gitlab.controller.api.ReviewRestApi;
import com.gitlab.dto.ReviewDto;
import com.gitlab.mapper.ReviewMapper;
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
    private final ReviewMapper reviewMapper;
    private final ReviewImageService reviewImageService;

    @Override
    public ResponseEntity<List<ReviewDto>> getAll() {
        var reviews = reviewService.findAll();
        return reviews.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(reviews.stream().map(reviewMapper::toDto).toList());
    }

    @Override
    public ResponseEntity<ReviewDto> get(Long id) {
        Optional<Review> ReviewOptional = reviewService.findById(id);

        return ReviewOptional.map(reviewMapper::toDto).map(ReviewDto -> ResponseEntity.status(HttpStatus.OK)
                .body(ReviewDto)).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

    }


    @Override
    public ResponseEntity<ReviewDto> create(ReviewDto reviewDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reviewMapper
                        .toDto(reviewService
                                .save(reviewMapper
                                        .toEntity(reviewDto))));
    }

    @Override
    public ResponseEntity<ReviewDto> update(Long id, ReviewDto reviewDto) {
        return reviewService.update(id, reviewMapper.toEntity(reviewDto))
                .map(Review -> ResponseEntity.ok(reviewMapper.toDto(Review)))
                .orElseGet(() -> ResponseEntity.notFound().build());
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