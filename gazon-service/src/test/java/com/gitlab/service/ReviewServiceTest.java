package com.gitlab.service;

import com.gitlab.enums.EntityStatus;
import com.gitlab.model.Review;
import com.gitlab.model.User;
import com.gitlab.repository.ReviewRepository;
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
public class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;
    @InjectMocks
    private ReviewService reviewService;

    @Test
    void should_find_all_reviews() {
        List<Review> expectedResult = generateReviews();
        when(reviewRepository.findAll()).thenReturn(generateReviews());

        List<Review> actualResult = reviewService.findAll();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void should_find_review_by_id() {
        long id = 1L;
        Review expectedResult = generateReview();
        when(reviewRepository.findById(id)).thenReturn(Optional.of(expectedResult));

        Optional<Review> actualResult = reviewService.findById(id);

        assertEquals(expectedResult, actualResult.orElse(null));
    }

    @Test
    void should_save_review() {
        Review expectedResult = generateReview();
        when(reviewRepository.save(expectedResult)).thenReturn(expectedResult);

        Review actualResult = reviewService.save(expectedResult);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void should_update_review() {
        long id = 2L;
        Review reviewToUpdate = generateReview();


        Review reviewBeforeUpdate = new Review();
        reviewBeforeUpdate.setId(id);
        reviewBeforeUpdate.setPros("pros22");
        reviewBeforeUpdate.setCons("cons22");
        reviewBeforeUpdate.setComment("comment22");
        reviewBeforeUpdate.setRating((byte) 2);
        reviewBeforeUpdate.setHelpfulCounter(3);
        reviewBeforeUpdate.setNotHelpfulCounter(22);


        Review updatedReview = generateReview();
        updatedReview.setId(id);

        when(reviewRepository.findById(id)).thenReturn(Optional.of(reviewBeforeUpdate));
        when(reviewRepository.save(updatedReview)).thenReturn(updatedReview);

        Optional<Review> actualResult = reviewService.update(id, reviewToUpdate);

        assertEquals(updatedReview, actualResult.orElse(null));
    }

    @Test
    void should_not_update_review_when_entity_not_found() {
        long id = 1L;
        Review reviewToUpdate = generateReview();

        when(reviewRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Review> actualResult = reviewService.update(id, reviewToUpdate);

        verify(reviewRepository, never()).save(any());
        assertNull(actualResult.orElse(null));
    }

    @Test
    void should_not_update_pros_field_if_null() {
        long id = 1L;
        Review reviewToUpdate = generateReview();
        reviewToUpdate.setPros(null);

        Review reviewBeforeUpdate = generateReview();

        when(reviewRepository.findById(id)).thenReturn(Optional.of(reviewBeforeUpdate));
        when(reviewRepository.save(reviewBeforeUpdate)).thenReturn(reviewBeforeUpdate);

        Optional<Review> actualResult = reviewService.update(id, reviewToUpdate);

        verify(reviewRepository).save(reviewBeforeUpdate);
        assertNotNull(actualResult.orElse(reviewBeforeUpdate).getPros());
    }

    @Test
    void should_not_update_cons_field_if_null() {
        long id = 1L;
        Review reviewToUpdate = generateReview();
        reviewToUpdate.setCons(null);

        Review reviewBeforeUpdate = generateReview();

        when(reviewRepository.findById(id)).thenReturn(Optional.of(reviewBeforeUpdate));
        when(reviewRepository.save(reviewBeforeUpdate)).thenReturn(reviewBeforeUpdate);

        Optional<Review> actualResult = reviewService.update(id, reviewToUpdate);

        verify(reviewRepository).save(reviewBeforeUpdate);
        assertNotNull(actualResult.orElse(reviewBeforeUpdate).getCons());
    }

    @Test
    void should_not_update_comment_field_if_null() {
        long id = 1L;
        Review reviewToUpdate = generateReview();
        reviewToUpdate.setComment(null);

        Review reviewBeforeUpdate = generateReview();

        when(reviewRepository.findById(id)).thenReturn(Optional.of(reviewBeforeUpdate));
        when(reviewRepository.save(reviewBeforeUpdate)).thenReturn(reviewBeforeUpdate);

        Optional<Review> actualResult = reviewService.update(id, reviewToUpdate);

        verify(reviewRepository).save(reviewBeforeUpdate);
        assertNotNull(actualResult.orElse(reviewBeforeUpdate).getComment());
    }

    @Test
    void should_not_update_rating_field_if_null() {
        long id = 1L;
        Review reviewToUpdate = generateReview();
        reviewToUpdate.setRating(null);

        Review reviewBeforeUpdate = generateReview();

        when(reviewRepository.findById(id)).thenReturn(Optional.of(reviewBeforeUpdate));
        when(reviewRepository.save(reviewBeforeUpdate)).thenReturn(reviewBeforeUpdate);

        Optional<Review> actualResult = reviewService.update(id, reviewToUpdate);

        verify(reviewRepository).save(reviewBeforeUpdate);
        assertNotNull(actualResult.orElse(reviewBeforeUpdate).getRating());
    }

    @Test
    void should_not_update_helpfulCounter_field_if_null() {
        long id = 1L;
        Review reviewToUpdate = generateReview();
        reviewToUpdate.setHelpfulCounter(null);

        Review reviewBeforeUpdate = generateReview();

        when(reviewRepository.findById(id)).thenReturn(Optional.of(reviewBeforeUpdate));
        when(reviewRepository.save(reviewBeforeUpdate)).thenReturn(reviewBeforeUpdate);

        Optional<Review> actualResult = reviewService.update(id, reviewToUpdate);

        verify(reviewRepository).save(reviewBeforeUpdate);
        assertNotNull(actualResult.orElse(reviewBeforeUpdate).getHelpfulCounter());
    }

    @Test
    void should_not_update_notHelpfulCounter_field_if_null() {
        long id = 1L;
        Review reviewToUpdate = generateReview();
        reviewToUpdate.setNotHelpfulCounter(null);

        Review reviewBeforeUpdate = generateReview();

        when(reviewRepository.findById(id)).thenReturn(Optional.of(reviewBeforeUpdate));
        when(reviewRepository.save(reviewBeforeUpdate)).thenReturn(reviewBeforeUpdate);

        Optional<Review> actualResult = reviewService.update(id, reviewToUpdate);

        verify(reviewRepository).save(reviewBeforeUpdate);
        assertNotNull(actualResult.orElse(reviewBeforeUpdate).getNotHelpfulCounter());
    }

    @Test
    void should_delete_review() {
        long id = 1L;
        Review deletedReview = generateReview(id);
        when(reviewRepository.findById(id)).thenReturn(Optional.of(deletedReview));

        reviewService.delete(id);

        verify(reviewRepository).save(deletedReview);
    }

    @Test
    void should_not_delete_review_when_entity_not_found() {
        long id = 1L;
        when(reviewRepository.findById(id)).thenReturn(Optional.empty());

        reviewService.delete(id);

        verify(reviewRepository, never()).deleteById(anyLong());
    }

    private List<Review> generateReviews() {
        return List.of(
                generateReview(1L),
                generateReview(2L),
                generateReview(3L),
                generateReview(4L),
                generateReview(5L)
        );
    }

    private Review generateReview(Long id) {
        Review review = generateReview();
        review.setId(id);
        return review;
    }

    private Review generateReview() {
        Review review = new Review();
        review.setId(1L);
        review.setPros("pros");
        review.setCons("cons");
        review.setComment("comment");
        review.setRating((byte) 6);
        review.setHelpfulCounter(7);
        review.setNotHelpfulCounter(1);
        review.setEntityStatus(EntityStatus.ACTIVE);
        return review;
    }
}
