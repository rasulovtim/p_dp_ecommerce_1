package com.gitlab.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ReviewImageDtoTest extends AbstractDtoTest {

    private ReviewImageDto getValidReviewImageDto() {
        var reviewImageDto = new ReviewImageDto();
        reviewImageDto.setReviewId(1L);
        reviewImageDto.setName("name1");
        reviewImageDto.setData(new byte[]{1});
        return reviewImageDto;
    }

    @Test
    void test_valid_reviewImageDto() {

        assertTrue(validator.validate(getValidReviewImageDto()).isEmpty());
    }

    @Test
    void test_invalid_max_name_length() {
        ReviewImageDto reviewImageDto = getValidReviewImageDto();
        reviewImageDto.setName("a".repeat(257));

        assertFalse(validator.validate(reviewImageDto).isEmpty());
        String expectedMessage = "Length of ReviewImage's name should be between 1 and 256 characters";
        assertEquals(expectedMessage, validator.validate(reviewImageDto).iterator().next().getMessage());
    }


    @Test
    void test_null_name() {
        ReviewImageDto reviewImageDto = getValidReviewImageDto();
        reviewImageDto.setName(null);

        assertFalse(validator.validate(reviewImageDto).isEmpty());
        String expectedMessage = "ReviewImage's name should not be empty";
        assertEquals(expectedMessage, validator.validate(reviewImageDto).iterator().next().getMessage());
    }

    //////////////////////////////////////////////////////////////////////////////////

    @Test
    void test_null_reviewId() {
        ReviewImageDto reviewImageDto = getValidReviewImageDto();
        reviewImageDto.setReviewId(null);

        assertFalse(validator.validate(reviewImageDto).isEmpty());
        String expectedMessage = "ReviewImage's reviewId should not be empty";
        assertEquals(expectedMessage, validator.validate(reviewImageDto).iterator().next().getMessage());
    }
    //////////////////////////////////////////////////////////////////////////////////

    @Test
    void test_null_data() {
        ReviewImageDto reviewImageDto = getValidReviewImageDto();
        reviewImageDto.setData(null);

        assertFalse(validator.validate(reviewImageDto).isEmpty());
        String expectedMessage = "ReviewImage's data should not be empty";
        assertEquals(expectedMessage, validator.validate(reviewImageDto).iterator().next().getMessage());
    }
}
