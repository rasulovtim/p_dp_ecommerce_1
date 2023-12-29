package com.gitlab.mapper;

import com.gitlab.controller.AbstractIntegrationTest;
import com.gitlab.dto.ReviewDto;
import com.gitlab.model.Product;
import com.gitlab.model.Review;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ReviewMapperTest extends AbstractIntegrationTest {

    @Autowired
    private ReviewMapper mapper;

    @Test
    void should_map_review_to_Dto() {
        Review review = getReview(1L);

        ReviewDto dtoTwin = mapper.toDto(review);

        assertNotNull(dtoTwin);
        assertEquals(review.getId(), dtoTwin.getId());
        assertEquals(review.getProduct().getId(), dtoTwin.getProductId());
        assertEquals(review.getPros(), dtoTwin.getPros());
        assertEquals(review.getCons(), dtoTwin.getCons());
        assertEquals(review.getRating(), dtoTwin.getRating());
    }

    @Test
    void should_map_reviewDto_to_Entity() {
        ReviewDto reviewDto = getReviewDto(1L);

        Review entityTwin = mapper.toEntity(reviewDto);

        assertNotNull(entityTwin);
        assertEquals(reviewDto.getId(), entityTwin.getId());
        assertEquals(reviewDto.getProductId(), entityTwin.getProduct().getId());
        assertEquals(reviewDto.getPros(), entityTwin.getPros());
        assertEquals(reviewDto.getCons(), entityTwin.getCons());
        assertEquals(reviewDto.getRating(), entityTwin.getRating());
    }

    @Test
    void should_map_reviewList_to_DtoList() {
        List<Review> reviewList = List.of(getReview(1L), getReview(2L), getReview(3L));

        List<ReviewDto> reviewDtoList = mapper.toDtoList(reviewList);

        assertNotNull(reviewDtoList);
        assertEquals(reviewList.size(), reviewList.size());
        for (int i = 0; i < reviewDtoList.size(); i++) {
            ReviewDto dto = reviewDtoList.get(i);
            Review entity = reviewList.get(i);
            assertEquals(dto.getId(), entity.getId());
            assertEquals(dto.getProductId(), entity.getProduct().getId());
            assertEquals(dto.getPros(), entity.getPros());
            assertEquals(dto.getCons(), entity.getCons());
            assertEquals(dto.getRating(), entity.getRating());
        }
    }

    @Test
    void should_map_reviewDtoList_to_EntityList() {
        List<ReviewDto> reviewDtoList = List.of(getReviewDto(1L), getReviewDto(2L), getReviewDto(3L));

        List<Review> reviewList = mapper.toEntityList(reviewDtoList);

        assertNotNull(reviewList);
        assertEquals(reviewList.size(), reviewList.size());
        for (int i = 0; i < reviewList.size(); i++) {
            ReviewDto dto = reviewDtoList.get(i);
            Review entity = reviewList.get(i);
            assertEquals(dto.getId(), entity.getId());
            assertEquals(dto.getProductId(), entity.getProduct().getId());
            assertEquals(dto.getPros(), entity.getPros());
            assertEquals(dto.getCons(), entity.getCons());
            assertEquals(dto.getRating(), entity.getRating());
        }
    }

    @NotNull
    private Review getReview(Long id) {
        Review review = new Review();
        review.setProduct(getProduct(id));
        review.setPros("pros" + id);
        review.setCons("cons" + id);
        review.setRating((byte) (id - 1));
        return review;
    }

    @NotNull
    private ReviewDto getReviewDto(Long id) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setProductId(id);
        reviewDto.setPros("pros" + id);
        reviewDto.setCons("cons" + id);
        reviewDto.setRating((byte) (id - 1));
        return reviewDto;
    }

    private Product getProduct(Long id) {
        Product product = new Product();
        product.setId(id);
        return product;
    }
}
