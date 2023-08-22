package com.gitlab.service;

import com.gitlab.dto.ReviewDto;
import com.gitlab.mapper.ReviewMapper;
import com.gitlab.model.Review;
import com.gitlab.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final ReviewMapper reviewMapper;

    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    public List<ReviewDto> findAllDto() {
        List<Review> reviews = reviewRepository.findAll();
        return reviews.stream()
                .map(reviewMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<Review> findById(Long id) {
        return reviewRepository.findById(id);
    }

    public Optional<ReviewDto> findByIdDto(Long id) {
        return reviewRepository.findById(id)
                .map(reviewMapper::toDto);
    }

    @Transactional
    public Review save(Review review) {
        return reviewRepository.save(review);
    }

    @Transactional
    public ReviewDto saveDto(ReviewDto reviewDto) {
        Review review = reviewMapper.toEntity(reviewDto);
        Review savedReview = reviewRepository.save(review);
        return reviewMapper.toDto(savedReview);
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
    public Optional<ReviewDto> updateDto(Long id, ReviewDto reviewDto) {
        Optional<Review> reviewOptional = reviewRepository.findById(id);
        if (reviewOptional.isEmpty()) {
            return Optional.empty();
        }

        Review currentReview = reviewOptional.get();
        if (reviewDto.getPros() != null) {
            currentReview.setPros(reviewDto.getPros());
        }
        if (reviewDto.getCons() != null) {
            currentReview.setCons(reviewDto.getCons());
        }
        if (reviewDto.getComment() != null) {
            currentReview.setComment(reviewDto.getComment());
        }
        if (reviewDto.getRating() != null) {
            currentReview.setRating(reviewDto.getRating());
        }
        if (reviewDto.getHelpfulCounter() != null) {
            currentReview.setHelpfulCounter(reviewDto.getHelpfulCounter());
        }
        if (reviewDto.getNotHelpfulCounter() != null) {
            currentReview.setNotHelpfulCounter(reviewDto.getNotHelpfulCounter());
        }

        Review updatedReview = reviewRepository.save(currentReview);
        return Optional.of(reviewMapper.toDto(updatedReview));
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