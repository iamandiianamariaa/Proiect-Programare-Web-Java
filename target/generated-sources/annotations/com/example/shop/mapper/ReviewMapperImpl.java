package com.example.shop.mapper;

import com.example.shop.domain.Product;
import com.example.shop.domain.Product.ProductBuilder;
import com.example.shop.domain.Review;
import com.example.shop.domain.Review.ReviewBuilder;
import com.example.shop.dto.ReviewDto;
import com.example.shop.dto.ReviewDto.ReviewDtoBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-01-10T21:59:58+0200",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.9 (Oracle Corporation)"
)
@Component
public class ReviewMapperImpl implements ReviewMapper {

    @Override
    public ReviewDto mapToDto(Review review) {
        if ( review == null ) {
            return null;
        }

        ReviewDtoBuilder reviewDto = ReviewDto.builder();

        reviewDto.productId( reviewProductId( review ) );
        reviewDto.id( review.getId() );
        reviewDto.customerName( review.getCustomerName() );
        reviewDto.review( review.getReview() );
        reviewDto.rating( review.getRating() );

        return reviewDto.build();
    }

    @Override
    public Review mapToEntity(ReviewDto reviewDto) {
        if ( reviewDto == null ) {
            return null;
        }

        ReviewBuilder review = Review.builder();

        review.product( reviewDtoToProduct( reviewDto ) );
        review.id( reviewDto.getId() );
        review.customerName( reviewDto.getCustomerName() );
        review.review( reviewDto.getReview() );
        review.rating( reviewDto.getRating() );

        return review.build();
    }

    @Override
    public Review update(ReviewDto reviewDto, Review review) {
        if ( reviewDto == null ) {
            return null;
        }

        if ( review.getProduct() == null ) {
            review.setProduct( new Product() );
        }
        reviewDtoToProduct1( reviewDto, review.getProduct() );
        review.setCustomerName( reviewDto.getCustomerName() );
        review.setReview( reviewDto.getReview() );
        review.setRating( reviewDto.getRating() );

        return review;
    }

    private Long reviewProductId(Review review) {
        if ( review == null ) {
            return null;
        }
        Product product = review.getProduct();
        if ( product == null ) {
            return null;
        }
        Long id = product.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected Product reviewDtoToProduct(ReviewDto reviewDto) {
        if ( reviewDto == null ) {
            return null;
        }

        ProductBuilder product = Product.builder();

        product.id( reviewDto.getProductId() );

        return product.build();
    }

    protected void reviewDtoToProduct1(ReviewDto reviewDto, Product mappingTarget) {
        if ( reviewDto == null ) {
            return;
        }

        mappingTarget.setId( reviewDto.getProductId() );
    }
}
