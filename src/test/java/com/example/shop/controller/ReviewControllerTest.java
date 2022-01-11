package com.example.shop.controller;

import com.example.shop.domain.Review;
import com.example.shop.dto.ReviewDto;
import com.example.shop.mapper.ReviewMapper;
import com.example.shop.service.ReviewService;
import com.example.shop.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ReviewController.class)
class ReviewControllerTest {

    private static final Long USER_ID = 1L;

    @MockBean
    private ReviewService reviewService;

    @MockBean
    private ReviewMapper reviewMapper;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createReview() throws Exception {
        ReviewDto reviewDto = getReviewDto();

        when(userService.checkIfUserHasUserRole(USER_ID)).thenReturn(Boolean.TRUE);
        when(reviewService.create(any())).thenReturn(reviewDto);

        MvcResult result = mockMvc.perform(post("/reviews/" + USER_ID)
                        .content(objectMapper.writeValueAsString(reviewDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(reviewDto));
    }

    @Test
    void getById() throws Exception {
        Long id = 1L;
        Review review1 = getReview();
        ReviewDto reviewDto = getReviewDto();

        when(reviewMapper.mapToDto(review1)).thenReturn(reviewDto);
        when(reviewService.getById(any())).thenReturn(review1);

        mockMvc.perform(get("/reviews/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void getAll() throws Exception {
        Review review1 = getReview();
        ReviewDto reviewDto = getReviewDto();
        List<Review> dto = List.of(review1);

        when(reviewMapper.mapToDto(review1)).thenReturn(reviewDto);
        when(reviewService.getAll()).thenReturn(dto);

        mockMvc.perform(get("/reviews"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void updateReview() throws Exception {
        Long id = 1L;
        Review review1 = getReview();
        ReviewDto reviewDto = getReviewDto();

        when(reviewService.update(any(), any())).thenReturn(review1);
        when(reviewMapper.mapToDto(review1)).thenReturn(reviewDto);
        when(userService.checkIfUserHasUserRole(USER_ID)).thenReturn(Boolean.TRUE);

        mockMvc.perform(put("/reviews/" + USER_ID + "/" + id)
                        .content(objectMapper.writeValueAsString(reviewDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void deleteById() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/reviews/" + id))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    ReviewDto getReviewDto(){
        Long id = 1L;
        String customerName = "anna";
        String review = "good product";
        Double rating = 4.5;

        ReviewDto reviewDto = ReviewDto.builder()
                .id(id)
                .customerName(customerName)
                .review(review)
                .rating(rating)
                .productId(2L)
                .build();

        return reviewDto;
    }

    Review getReview(){
        Long id = 1L;
        String customerName = "anna";
        String review = "good product";
        Double rating = 4.5;
        LocalDateTime dateAdded = LocalDateTime.now();

        Review review1 = Review.builder()
                .id(id)
                .customerName(customerName)
                .review(review)
                .rating(rating)
                .reviewAddedDate(dateAdded)
                .build();

        return review1;
    }
}