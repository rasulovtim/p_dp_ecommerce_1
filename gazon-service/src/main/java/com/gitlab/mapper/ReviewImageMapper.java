package com.gitlab.mapper;

import com.gitlab.dto.ReviewImageDto;
import com.gitlab.model.Review;
import com.gitlab.model.ReviewImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class ReviewImageMapper {

    @PersistenceContext
    private EntityManager entityManager;

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

        return entityManager.find(Review.class, reviewId);
    }
}