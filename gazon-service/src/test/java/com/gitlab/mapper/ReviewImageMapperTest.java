package com.gitlab.mapper;

import com.gitlab.controller.AbstractIntegrationTest;
import com.gitlab.dto.ReviewImageDto;
import com.gitlab.model.Review;
import com.gitlab.model.ReviewImage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ReviewImageMapperTest extends AbstractIntegrationTest {

    @Autowired
    private ReviewImageMapper mapper;

    @Test
    void should_map_reviewImage_to_Dto() {
        ReviewImage reviewImage = getReviewImage(1L);

        ReviewImageDto dtoTwin = mapper.toDto(reviewImage);

        assertNotNull(dtoTwin);
        assertEquals(reviewImage.getId(), dtoTwin.getId());
        assertEquals(reviewImage.getReview().getId(), dtoTwin.getReviewId());
        assertEquals(reviewImage.getName(), dtoTwin.getName());
        assertEquals(Arrays.toString(reviewImage.getData()), Arrays.toString(dtoTwin.getData()));
    }

    @Test
    void should_map_reviewImageDto_to_Entity() {
        ReviewImageDto reviewImageDto = getReviewImageDto(2L);

        ReviewImage entityTwin = mapper.toEntity(reviewImageDto);

        assertNotNull(entityTwin);
        assertEquals(reviewImageDto.getId(), entityTwin.getId());
        assertEquals(reviewImageDto.getReviewId(), entityTwin.getReview().getId());
        assertEquals(reviewImageDto.getName(), entityTwin.getName());
        assertEquals(Arrays.toString(reviewImageDto.getData()), Arrays.toString(entityTwin.getData()));
    }

    @Test
    void should_map_reviewImageList_to_DtoList() {
        List<ReviewImage> reviewImageList = List.of(getReviewImage(1L), getReviewImage(2L), getReviewImage(3L));

        List<ReviewImageDto> reviewImageDtoList = mapper.toDtoList(reviewImageList);

        assertNotNull(reviewImageDtoList);
        assertEquals(reviewImageList.size(), reviewImageList.size());
        for (int i = 0; i < reviewImageDtoList.size(); i++) {
            ReviewImageDto dto = reviewImageDtoList.get(i);
            ReviewImage entity = reviewImageList.get(i);
            assertEquals(dto.getId(), entity.getId());
            assertEquals(dto.getReviewId(), entity.getReview().getId());
            assertEquals(dto.getName(), entity.getName());
            assertEquals(Arrays.toString(dto.getData()), Arrays.toString(entity.getData()));
        }
    }

    @Test
    void should_map_reviewImageDtoList_to_EntityList() {
        List<ReviewImageDto> reviewImageDtoList = List.of(getReviewImageDto(1L), getReviewImageDto(2L), getReviewImageDto(3L));

        List<ReviewImage> reviewImageList = mapper.toEntityList(reviewImageDtoList);

        assertNotNull(reviewImageList);
        assertEquals(reviewImageList.size(), reviewImageList.size());
        for (int i = 0; i < reviewImageList.size(); i++) {
            ReviewImageDto dto = reviewImageDtoList.get(i);
            ReviewImage entity = reviewImageList.get(i);
            assertEquals(dto.getId(), entity.getId());
            assertEquals(dto.getReviewId(), entity.getReview().getId());
            assertEquals(dto.getName(), entity.getName());
            assertEquals(Arrays.toString(dto.getData()), Arrays.toString(entity.getData()));
        }
    }

    private Review getReview(Long id) {
        Review review = new Review();
        review.setId(id);
        return review;
    }

    private ReviewImage getReviewImage(Long id) {
        ReviewImage reviewImage = new ReviewImage();
        reviewImage.setReview(getReview(id));
        reviewImage.setName("name" + id);
        reviewImage.setData(new byte[]{(byte)(id % 128)});
        return reviewImage;
    }

    private ReviewImageDto getReviewImageDto(Long id) {
        ReviewImageDto reviewImageDto = new ReviewImageDto();
        reviewImageDto.setReviewId(id);
        reviewImageDto.setName("name" + id);
        reviewImageDto.setData(new byte[]{(byte)(id % 128)});
        return reviewImageDto;
    }

}


