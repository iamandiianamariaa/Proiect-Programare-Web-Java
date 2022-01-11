package com.example.shop.service;

import com.example.shop.domain.Product;
import com.example.shop.domain.Review;
import com.example.shop.dto.ReviewDto;
import com.example.shop.exception.EntityNotFoundException;
import com.example.shop.mapper.ReviewMapper;
import com.example.shop.repository.ProductRepository;
import com.example.shop.repository.ReviewRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    ReviewRepository reviewRepository;

    @Mock
    ProductRepository productRepository;

    @Mock
    ReviewMapper reviewMapper;

    @InjectMocks
    ReviewService reviewService;

    @Test
    @DisplayName("Save review")
    void saveReview() {
        Long id = 1L;
        String customerName = "anna";
        String review = "good product";
        Double rating = 4.5;
        LocalDateTime dateAdded = LocalDateTime.now();

        Product product = Product.builder()
                .id(2L)
                .name("face cream")
                .brand("Nivea")
                .size("50 ml")
                .description("fdf")
                .ingredients("ffd")
                .instructions("fdffd")
                .price(32.3)
                .reviewScore(3.6)
                .build();

        ReviewDto reviewDto = ReviewDto.builder()
                .id(id)
                .customerName(customerName)
                .review(review)
                .rating(rating)
                .productId(2L)
                .build();

        Review reviewToBeSaved = Review.builder()
                .id(id)
                .customerName(customerName)
                .review(review)
                .rating(rating)
                .reviewAddedDate(dateAdded)
                .product(product)
                .build();

        Review reviewSaved = Review.builder()
                .id(id)
                .customerName(customerName)
                .review(review)
                .rating(rating)
                .reviewAddedDate(dateAdded)
                .product(product)
                .build();

        when(reviewRepository.save(reviewToBeSaved)).thenReturn(reviewSaved);

        when(reviewMapper.mapToEntity(reviewDto)).thenReturn(reviewToBeSaved);
        doNothing().when(productRepository).setAverageScore(reviewToBeSaved.getProduct().getId());
        when(reviewMapper.mapToDto(reviewSaved)).thenReturn(reviewDto);

        ReviewDto result = reviewService.create(reviewDto);

        assertEquals(reviewSaved.getId(), result.getId());
        assertEquals(reviewSaved.getCustomerName(), result.getCustomerName());
        assertEquals(reviewSaved.getReview(), result.getReview());
        assertEquals(reviewSaved.getRating(), result.getRating());
    }

    @Test
    @DisplayName("Get review by ID successful")
    void getReviewById() {
        Long id = 1L;
        Review review = Review.builder()
                .id(id)
                .customerName("anna")
                .review("good product")
                .rating(4.5)
                .reviewAddedDate(LocalDateTime.now())
                .build();


        when(reviewRepository.findById(id)).thenReturn(Optional.of(review));

        Review result = reviewService.getById(id);

        assertEquals(review, result);
    }

    @Test
    @DisplayName("Get review by ID FAILED")
    void getReviewByIdFailed() {
        Long id = 1L;

        when(reviewRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> reviewService.getById(id));
    }

    @Test
    @DisplayName("Update review")
    void updateReview() {
        Long id = 1L;
        String customerName = "anna";
        String review = "good product";
        Double rating = 4.5;
        LocalDateTime dateAdded = LocalDateTime.now();

        String nameUpdated = "john";


        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setCustomerName(nameUpdated);
        reviewDto.setReview(review);
        reviewDto.setRating(rating);
        reviewDto.setProductId(2L);

        Product product = Product.builder()
                .id(2L)
                .name("face cream")
                .brand("Nivea")
                .size("50 ml")
                .description("fdf")
                .ingredients("ffd")
                .instructions("fdffd")
                .price(32.3)
                .reviewScore(3.6)
                .build();

        Review reviewToBeUpdated = Review.builder()
                .id(id)
                .customerName(customerName)
                .reviewAddedDate(dateAdded)
                .review(review)
                .rating(rating)
                .product(product)
                .build();

        Review reviewUpdated = Review.builder()
                .id(id)
                .customerName(nameUpdated)
                .rating(rating)
                .review(review)
                .reviewAddedDate(dateAdded)
                .product(product)
                .build();

        Review reviewSaved = Review.builder()
                .id(id)
                .customerName(nameUpdated)
                .rating(rating)
                .review(review)
                .reviewAddedDate(dateAdded)
                .product(product)
                .build();


        doNothing().when(productRepository).setAverageScore(reviewDto.getProductId());
        when(reviewRepository.findById(id)).thenReturn(Optional.of(reviewToBeUpdated));
        when(reviewMapper.update(reviewDto, reviewToBeUpdated)).thenReturn(reviewUpdated);

        when(reviewRepository.save(any())).thenReturn(reviewSaved);

        Review result = reviewService.update(id, reviewDto);

        assertEquals(reviewSaved, result);
    }

    @Test
    @DisplayName("Get all reviews")
    void getAllReviews() {
        Long id = 1L;
        Review review1 = Review.builder()
                .id(id)
                .customerName("anna")
                .review("good product")
                .rating(4.5)
                .reviewAddedDate(LocalDateTime.now())
                .build();

        Review review2 = Review.builder()
                .id(id)
                .customerName("john")
                .review("bad product")
                .rating(3.0)
                .reviewAddedDate(LocalDateTime.now())
                .build();

        when(reviewRepository.findAll()).thenReturn(Arrays.asList(review1, review2));

        List<Review> result = reviewService.getAll();

        assertEquals(result.get(0), review1);
        assertEquals(result.get(1), review2);
    }

    @Test
    @DisplayName("Delete review")
    void deleteReview() {
        Long id = 1L;

        Product product = Product.builder()
                .id(2L)
                .name("face cream")
                .brand("Nivea")
                .size("50 ml")
                .description("fdf")
                .ingredients("ffd")
                .instructions("fdffd")
                .price(32.3)
                .reviewScore(3.6)
                .build();

        Review review = Review.builder()
                .id(id)
                .customerName("anna")
                .review("good product")
                .rating(4.5)
                .reviewAddedDate(LocalDateTime.now())
                .product(product)
                .build();

        doNothing().when(reviewRepository).deleteById(id);
        when(reviewRepository.findById(id)).thenReturn(Optional.ofNullable(review));
        when(reviewRepository.findById(id)).thenReturn(Optional.of(review));
        doNothing().when(productRepository).setAverageScore(review.getProduct().getId());

        reviewService.deleteById(id);

        verify(reviewRepository, times(1)).deleteById(id);
    }
}