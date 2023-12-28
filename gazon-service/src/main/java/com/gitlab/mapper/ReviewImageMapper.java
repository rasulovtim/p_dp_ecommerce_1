package com.gitlab.mapper;

import com.gitlab.dto.ReviewImageDto;
import com.gitlab.model.Review;
import com.gitlab.model.ReviewImage;
import com.gitlab.repository.ReviewRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class ReviewImageMapper {

    @Autowired
    private ReviewRepository reviewRepository;

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

        return reviewRepository.findById(reviewId).orElse(null);
    }

    public List<ReviewImageDto> toDtoList(List<ReviewImage> reviewImageList) {
        return reviewImageList.stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<ReviewImage> toEntityList(List<ReviewImageDto> reviewImageDtoList) {
        return reviewImageDtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
