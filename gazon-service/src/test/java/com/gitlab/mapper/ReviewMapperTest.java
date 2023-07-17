package com.gitlab.mapper;

import com.gitlab.controller.AbstractIntegrationTest;
import com.gitlab.dto.ReviewDto;
import com.gitlab.model.Product;
import com.gitlab.model.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ReviewMapperTest extends AbstractIntegrationTest {

    @Autowired
    private ReviewMapper mapper;

    @Test
    void should_map_review_to_Dto() {
        Review review = new Review();
        review.setProduct(getProduct(1L));
        review.setPros("pros1");
        review.setCons("cons1");
        review.setRating((byte) 4);

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
        getProduct(2L);
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setProductId(2L);
        reviewDto.setPros("pros2");
        reviewDto.setCons("cons2");
        reviewDto.setRating((byte) 6);

        Review entityTwin = mapper.toEntity(reviewDto);

        assertNotNull(entityTwin);
        assertEquals(reviewDto.getId(), entityTwin.getId());
        assertEquals(reviewDto.getProductId(), entityTwin.getProduct().getId());
        assertEquals(reviewDto.getPros(), entityTwin.getPros());
        assertEquals(reviewDto.getCons(), entityTwin.getCons());
        assertEquals(reviewDto.getRating(), entityTwin.getRating());
    }

    private Product getProduct(Long id) {
        Product product = new Product();
        product.setId(id);
        return product;
    }
}
