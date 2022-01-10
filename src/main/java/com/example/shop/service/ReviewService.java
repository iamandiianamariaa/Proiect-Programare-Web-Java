package com.example.shop.service;

import com.example.shop.domain.Review;
import com.example.shop.dto.ReviewDto;
import com.example.shop.exception.EntityNotFoundException;
import com.example.shop.mapper.ReviewMapper;
import com.example.shop.repository.ProductRepository;
import com.example.shop.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ReviewMapper reviewMapper;

    @Transactional
    public ReviewDto create(ReviewDto reviewDto) {
        Review review = reviewMapper.mapToEntity(reviewDto);
        review.setReviewAddedDate(LocalDateTime.now());
        Review savedReview = reviewRepository.save(review);
        productRepository.setAverageScore(review.getProduct().getId());

        return reviewMapper.mapToDto(savedReview);
    }

    public Review getById(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> EntityNotFoundException.builder()
                        .entityId(reviewId)
                        .entityName("Review")
                        .build()
                );
    }

    @Transactional
    public Review update(Long reviewId, ReviewDto reviewDto) {
        Review review = getById(reviewId);
        Review updatedReview = reviewMapper.update(reviewDto, review);
        Review savedReview = reviewRepository.save(updatedReview);
        productRepository.setAverageScore(reviewDto.getProductId());

        return savedReview;
    }

    public List<Review> getAll() {
        return reviewRepository.findAll();
    }

    public void deleteById(Long id) {
        reviewRepository.deleteById(id);
        productRepository.setAverageScore(getById(id).getProduct().getId());
    }
}
