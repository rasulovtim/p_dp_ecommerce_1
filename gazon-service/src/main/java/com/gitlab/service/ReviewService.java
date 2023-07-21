package com.gitlab.service;

import com.gitlab.model.Review;
import com.gitlab.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    public Optional<Review> findById(Long id) {
        return reviewRepository.findById(id);
    }

    @Transactional
    public Review save(Review review) {
        return reviewRepository.save(review);
    }

    @Transactional
    public Optional<Review> update(Long id, Review review) {
        Optional<Review> reviewOptional = findById(id);
        Review currentReview;
        if (reviewOptional.isEmpty()) {
            return reviewOptional;
        } else {
            currentReview = reviewOptional.get();
        }
        if (review.getPros() != null) {
            currentReview.setPros(review.getPros());
        }
        if (review.getCons() != null) {
            currentReview.setCons(review.getCons());
        }
        if (review.getComment() != null) {
            currentReview.setComment(review.getComment());
        }
        if (review.getRating() != null) {
            currentReview.setRating(review.getRating());
        }
        if (review.getHelpfulCounter() != null) {
            currentReview.setHelpfulCounter(review.getHelpfulCounter());
        }
        if (review.getNotHelpfulCounter() != null) {
            currentReview.setNotHelpfulCounter(review.getNotHelpfulCounter());
        }
        return Optional.of(reviewRepository.save(currentReview));
    }

    @Transactional
    public Optional<Review> delete(Long id) {
        Optional<Review> reviewOptional = findById(id);
        if (reviewOptional.isPresent()) {
            reviewRepository.deleteById(id);
        }
        return reviewOptional;
    }
}