package com.example.shop.controller;

import com.example.shop.domain.Review;
import com.example.shop.dto.ReviewDto;
import com.example.shop.mapper.ReviewMapper;
import com.example.shop.service.ReviewService;
import com.example.shop.validator.ValidUser;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Validated
@RequestMapping("/reviews")
@Api(value = "/reviews",
        tags = "Reviews")
public class ReviewController {
    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;

    @Autowired
    public ReviewController(ReviewService reviewService, ReviewMapper reviewMapper) {
        this.reviewService = reviewService;
        this.reviewMapper = reviewMapper;
    }


    @PostMapping("/{userId}")
    @Operation(
            method = "POST",
            summary = "Add a review"
    )
    public ResponseEntity<ReviewDto> createReview(@PathVariable @ValidUser Long userId,
                                                  @RequestBody @Valid ReviewDto reviewDto) {
        return ResponseEntity
                .ok()
                .body(reviewService.create(reviewDto));
    }

    @GetMapping("/{reviewId}")
    @Operation(
            method = "GET",
            summary = "Get review by id"
    )
    public ResponseEntity<ReviewDto> getById(@PathVariable Long reviewId) {

        ReviewDto result = reviewMapper.mapToDto(reviewService.getById(reviewId));

        return ResponseEntity
                .ok()
                .body(result);
    }

    @GetMapping
    @Operation(
            method = "GET",
            summary = "Get all reviews"
    )
    public ResponseEntity<List<ReviewDto>> getAll() {

        List<ReviewDto> result = reviewService.getAll()
                .stream().map(reviewMapper::mapToDto)
                .collect(Collectors.toList());

        return ResponseEntity
                .ok()
                .body(result);
    }

    @PutMapping("/{userId}/{reviewId}")
    @Operation(
            method = "PUT",
            summary = "Update review by id"
    )
    public ResponseEntity<ReviewDto> updateReview(@PathVariable @ValidUser Long userId,
                                                  @PathVariable Long reviewId,
                                                  @RequestBody @Valid ReviewDto reviewDto) {
        Review savedReview = reviewService.update(reviewId, reviewDto);

        return ResponseEntity
                .ok()
                .body(reviewMapper.mapToDto(savedReview));
    }

    @DeleteMapping("/{reviewId}")
    @Operation(
            method = "DELETE",
            summary = "Delete review by id"
    )
    public ResponseEntity<?> deleteById(@PathVariable Long reviewId) {

        reviewService.deleteById(reviewId);

        return ResponseEntity
                .noContent()
                .build();
    }

}
