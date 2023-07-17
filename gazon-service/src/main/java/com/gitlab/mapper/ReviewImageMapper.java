package com.gitlab.mapper;

import com.gitlab.dto.ReviewImageDto;
import com.gitlab.model.Review;
import com.gitlab.model.ReviewImage;
import com.gitlab.service.ReviewService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class ReviewImageMapper {

    @Autowired
    protected ReviewService reviewService;

    @Mapping(source = "review", target = "reviewId")
    public abstract ReviewImageDto toDto(ReviewImage reviewImage);


    public Long mapReviewToReviewId(Review review) {
        if (review == null) {
            return null;
        }
        return review.getId();
    }

    @Mapping(source = "reviewId", target = "review")
    public abstract ReviewImage toEntity(ReviewImageDto reviewImageDto);


    public Review mapReviewIdToReview(Long reviewId) {
        if (reviewId == null) {
            return null;
        }

        return reviewService.findById(reviewId).
                orElseThrow(() -> new RuntimeException("Review wasn't found"));
    }
}
