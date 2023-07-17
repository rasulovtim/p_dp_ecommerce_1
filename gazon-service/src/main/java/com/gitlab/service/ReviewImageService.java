package com.gitlab.service;

import com.gitlab.model.ReviewImage;
import com.gitlab.repository.ReviewImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewImageService {

    private final ReviewImageRepository reviewImageRepository;


    public List<ReviewImage> findAll() {
        return reviewImageRepository.findAll();
    }

    public Optional<ReviewImage> findById(Long id) {
        return reviewImageRepository.findById(id);
    }

    @Transactional
    public ReviewImage save(ReviewImage reviewImage) {
        return reviewImageRepository.save(reviewImage);
    }


    @Transactional
    public Optional<ReviewImage> update(Long id, ReviewImage reviewImage) {
        Optional<ReviewImage> imageOptional = findById(id);
        ReviewImage currentReviewImage;
        if (imageOptional.isEmpty()) {
            return imageOptional;
        } else {
            currentReviewImage = imageOptional.get();
        }
        if (reviewImage.getName() != null) {
            currentReviewImage.setName(reviewImage.getName());
        }
        if (reviewImage.getData() != null) {
            currentReviewImage.setData(reviewImage.getData());
        }
        return Optional.of(reviewImageRepository.save(currentReviewImage));
    }

    @Transactional
    public Optional<ReviewImage> delete(Long id) {
        Optional<ReviewImage> imageOptional = findById(id);
        if (imageOptional.isPresent()) {
            reviewImageRepository.deleteById(id);
        }
        return imageOptional;
    }

    @Transactional
    public List<ReviewImage> saveAll(List<ReviewImage> imageList) {
        return reviewImageRepository.saveAll(imageList);
    }
}
