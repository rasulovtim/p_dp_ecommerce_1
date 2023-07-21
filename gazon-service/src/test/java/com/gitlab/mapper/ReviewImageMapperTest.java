package com.gitlab.mapper;

import com.gitlab.controller.AbstractIntegrationTest;
import com.gitlab.dto.ReviewImageDto;
import com.gitlab.model.Review;
import com.gitlab.model.ReviewImage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ReviewImageMapperTest extends AbstractIntegrationTest {

    @Autowired
    private ReviewImageMapper mapper;

    private Review getReview(Long id) {
        Review review = new Review();
        review.setId(id);
        return review;
    }

    @Test
    void should_map_reviewImage_to_Dto() {
        ReviewImage reviewImage = new ReviewImage();
        reviewImage.setReview(getReview(1L));
        reviewImage.setName("name1");
        reviewImage.setData(new byte[]{1});

        ReviewImageDto dtoTwin = mapper.toDto(reviewImage);

        assertNotNull(dtoTwin);
        assertEquals(reviewImage.getId(), dtoTwin.getId());
        assertEquals(reviewImage.getReview().getId(), dtoTwin.getReviewId());
        assertEquals(reviewImage.getName(), dtoTwin.getName());
        assertEquals(Arrays.toString(reviewImage.getData()), Arrays.toString(dtoTwin.getData()));
    }

    @Test
    void should_map_reviewImageDto_to_Entity() {
        getReview(2L);
        ReviewImageDto reviewImageDto = new ReviewImageDto();
        reviewImageDto.setReviewId(2L);
        reviewImageDto.setName("name2");
        reviewImageDto.setData(new byte[]{2});

        ReviewImage entityTwin = mapper.toEntity(reviewImageDto);

        assertNotNull(entityTwin);
        assertEquals(reviewImageDto.getId(), entityTwin.getId());
        assertEquals(reviewImageDto.getReviewId(), entityTwin.getReview().getId());
        assertEquals(reviewImageDto.getName(), entityTwin.getName());
        assertEquals(Arrays.toString(reviewImageDto.getData()), Arrays.toString(entityTwin.getData()));
    }
}


