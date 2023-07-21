package com.gitlab.service;

import com.gitlab.model.ReviewImage;
import com.gitlab.repository.ReviewImageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
public class ReviewImageServiceTest {

    @Mock
    private ReviewImageRepository reviewImageRepository;
    @InjectMocks
    private ReviewImageService reviewImageService;

    @Test
    void should_find_all_reviewImages() {
        List<ReviewImage> expectedResult = generateReviewImages();
        when(reviewImageRepository.findAll()).thenReturn(generateReviewImages());

        List<ReviewImage> actualResult = reviewImageService.findAll();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void should_find_reviewImage_by_id() {
        long id = 1L;
        ReviewImage expectedResult = generateReviewImage();
        when(reviewImageRepository.findById(id)).thenReturn(Optional.of(expectedResult));

        Optional<ReviewImage> actualResult = reviewImageService.findById(id);

        assertEquals(expectedResult, actualResult.orElse(null));
    }

    @Test
    void should_save_reviewImage() {
        ReviewImage expectedResult = generateReviewImage();
        when(reviewImageRepository.save(expectedResult)).thenReturn(expectedResult);

        ReviewImage actualResult = reviewImageService.save(expectedResult);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void should_update_reviewImage() {
        long id = 2L;
        ReviewImage reviewImageToUpdate = generateReviewImage();

        ReviewImage reviewImageBeforeUpdate = new ReviewImage();
        reviewImageBeforeUpdate.setId(id);
        reviewImageBeforeUpdate.setName("name22");
        reviewImageBeforeUpdate.setData(new byte[]{2, 3});

        ReviewImage updatedReviewImage = generateReviewImage();
        updatedReviewImage.setId(id);

        when(reviewImageRepository.findById(id)).thenReturn(Optional.of(reviewImageBeforeUpdate));
        when(reviewImageRepository.save(updatedReviewImage)).thenReturn(updatedReviewImage);

        Optional<ReviewImage> actualResult = reviewImageService.update(id, reviewImageToUpdate);

        assertEquals(updatedReviewImage, actualResult.orElse(null));
    }

    @Test
    void should_not_update_reviewImage_when_entity_not_found() {
        long id = 1L;
        ReviewImage reviewImageToUpdate = generateReviewImage();

        when(reviewImageRepository.findById(id)).thenReturn(Optional.empty());

        Optional<ReviewImage> actualResult = reviewImageService.update(id, reviewImageToUpdate);

        verify(reviewImageRepository, never()).save(any());
        assertNull(actualResult.orElse(null));
    }

    @Test
    void should_not_update_name_field_if_null() {
        long id = 1L;
        ReviewImage reviewImageToUpdate = generateReviewImage();
        reviewImageToUpdate.setName(null);

        ReviewImage reviewImageBeforeUpdate = generateReviewImage();

        when(reviewImageRepository.findById(id)).thenReturn(Optional.of(reviewImageBeforeUpdate));
        when(reviewImageRepository.save(reviewImageBeforeUpdate)).thenReturn(reviewImageBeforeUpdate);

        Optional<ReviewImage> actualResult = reviewImageService.update(id, reviewImageToUpdate);

        verify(reviewImageRepository).save(reviewImageBeforeUpdate);
        assertNotNull(actualResult.orElse(reviewImageBeforeUpdate).getName());
    }

    @Test
    void should_not_update_data_field_if_null() {
        long id = 1L;
        ReviewImage reviewImageToUpdate = generateReviewImage();
        reviewImageToUpdate.setData(null);

        ReviewImage reviewImageBeforeUpdate = generateReviewImage();

        when(reviewImageRepository.findById(id)).thenReturn(Optional.of(reviewImageBeforeUpdate));
        when(reviewImageRepository.save(reviewImageBeforeUpdate)).thenReturn(reviewImageBeforeUpdate);

        Optional<ReviewImage> actualResult = reviewImageService.update(id, reviewImageToUpdate);

        verify(reviewImageRepository).save(reviewImageBeforeUpdate);
        assertNotNull(actualResult.orElse(reviewImageBeforeUpdate).getData());
    }

    @Test
    void should_delete_reviewImage() {
        long id = 1L;
        when(reviewImageRepository.findById(id)).thenReturn(Optional.of(generateReviewImage()));

        reviewImageService.delete(id);

        verify(reviewImageRepository).deleteById(id);
    }

    @Test
    void should_not_delete_reviewImage_when_entity_not_found() {
        long id = 1L;
        when(reviewImageRepository.findById(id)).thenReturn(Optional.empty());

        reviewImageService.delete(id);

        verify(reviewImageRepository, never()).deleteById(anyLong());
    }

    private List<ReviewImage> generateReviewImages() {
        return List.of(
                generateReviewImage(1L),
                generateReviewImage(2L),
                generateReviewImage(3L),
                generateReviewImage(4L),
                generateReviewImage(5L)
        );
    }

    private ReviewImage generateReviewImage(Long id) {
        ReviewImage reviewImage = generateReviewImage();
        reviewImage.setId(id);
        return reviewImage;
    }

    private ReviewImage generateReviewImage() {
        ReviewImage reviewImage = new ReviewImage();
        reviewImage.setId(1L);
        reviewImage.setName("name1");
        reviewImage.setData(new byte[]{1});
        return reviewImage;
    }
}
