package com.example.shop.mapper;

import com.example.shop.domain.Category;
import com.example.shop.domain.Product;
import com.example.shop.domain.Product.ProductBuilder;
import com.example.shop.dto.ProductDto;
import com.example.shop.dto.ProductDto.ProductDtoBuilder;
import com.example.shop.dto.ProductRequestDto;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-01-11T11:33:27+0200",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.9 (Oracle Corporation)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Autowired
    private CustomMapper customMapper;

    @Override
    public ProductDto mapToDto(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductDtoBuilder productDto = ProductDto.builder();

        productDto.categoriesList( customMapper.categoryToDto( product.getCategories() ) );
        productDto.id( product.getId() );
        productDto.name( product.getName() );
        productDto.brand( product.getBrand() );
        productDto.price( product.getPrice() );
        productDto.size( product.getSize() );
        productDto.description( product.getDescription() );
        productDto.ingredients( product.getIngredients() );
        productDto.instructions( product.getInstructions() );
        productDto.reviewScore( product.getReviewScore() );

        return productDto.build();
    }

    @Override
    public Product mapToEntity(ProductRequestDto productDto) {
        if ( productDto == null ) {
            return null;
        }

        ProductBuilder product = Product.builder();

        product.categories( customMapper.idsToCategories( productDto.getCategoryIds() ) );
        product.id( productDto.getId() );
        product.name( productDto.getName() );
        product.brand( productDto.getBrand() );
        product.price( productDto.getPrice() );
        product.size( productDto.getSize() );
        product.description( productDto.getDescription() );
        product.ingredients( productDto.getIngredients() );
        product.instructions( productDto.getInstructions() );

        return product.build();
    }

    @Override
    public Product update(ProductRequestDto productRequestDto, Product product) {
        if ( productRequestDto == null ) {
            return null;
        }

        if ( product.getCategories() != null ) {
            List<Category> list = customMapper.idsToCategories( productRequestDto.getCategoryIds() );
            if ( list != null ) {
                product.getCategories().clear();
                product.getCategories().addAll( list );
            }
            else {
                product.setCategories( null );
            }
        }
        else {
            List<Category> list = customMapper.idsToCategories( productRequestDto.getCategoryIds() );
            if ( list != null ) {
                product.setCategories( list );
            }
        }
        product.setName( productRequestDto.getName() );
        product.setBrand( productRequestDto.getBrand() );
        product.setPrice( productRequestDto.getPrice() );
        product.setSize( productRequestDto.getSize() );
        product.setDescription( productRequestDto.getDescription() );
        product.setIngredients( productRequestDto.getIngredients() );
        product.setInstructions( productRequestDto.getInstructions() );

        return product;
    }
}
