package com.example.shop.mapper;

import com.example.shop.domain.Order;
import com.example.shop.domain.Order.OrderBuilder;
import com.example.shop.domain.OrderDetails;
import com.example.shop.domain.Product;
import com.example.shop.domain.User;
import com.example.shop.dto.OrderDetailsDto;
import com.example.shop.dto.OrderDetailsDto.OrderDetailsDtoBuilder;
import com.example.shop.dto.OrderDto;
import com.example.shop.dto.OrderDto.OrderDtoBuilder;
import com.example.shop.dto.OrderRequestDto;
import com.example.shop.dto.UserDto;
import com.example.shop.dto.UserDto.UserDtoBuilder;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-01-10T21:59:58+0200",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.9 (Oracle Corporation)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Autowired
    private ProductListMapper productListMapper;
    @Autowired
    private CustomMapper customMapper;

    @Override
    public OrderDto mapToDto(Order order) {
        if ( order == null ) {
            return null;
        }

        OrderDtoBuilder orderDto = OrderDto.builder();

        orderDto.orderProducts( productListMapper.orderToDto( order.getProducts() ) );
        orderDto.id( order.getId() );
        orderDto.price( order.getPrice() );
        orderDto.status( order.getStatus() );
        orderDto.noProducts( order.getNoProducts() );
        orderDto.orderAddedDate( order.getOrderAddedDate() );
        orderDto.user( userToUserDto( order.getUser() ) );
        orderDto.orderDetails( orderDetailsToOrderDetailsDto( order.getOrderDetails() ) );

        return orderDto.build();
    }

    @Override
    public Order mapToEntity(OrderRequestDto orderRequestDto) {
        if ( orderRequestDto == null ) {
            return null;
        }

        OrderBuilder order = Order.builder();

        order.user( customMapper.idToUser( orderRequestDto.getUserId() ) );
        order.orderDetails( customMapper.idToOrderDetails( orderRequestDto.getOrderDetailsId() ) );
        order.products( productListMapper.idsToProducts( orderRequestDto.getProductIds() ) );
        order.id( orderRequestDto.getId() );

        return order.build();
    }

    @Override
    public Order update(OrderRequestDto orderRequestDto, Order order) {
        if ( orderRequestDto == null ) {
            return null;
        }

        if ( order.getProducts() != null ) {
            List<Product> list = productListMapper.idsToProducts( orderRequestDto.getProductIds() );
            if ( list != null ) {
                order.getProducts().clear();
                order.getProducts().addAll( list );
            }
            else {
                order.setProducts( null );
            }
        }
        else {
            List<Product> list = productListMapper.idsToProducts( orderRequestDto.getProductIds() );
            if ( list != null ) {
                order.setProducts( list );
            }
        }

        return order;
    }

    protected UserDto userToUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDtoBuilder userDto = UserDto.builder();

        userDto.id( user.getId() );
        userDto.username( user.getUsername() );
        userDto.name( user.getName() );
        userDto.userType( user.getUserType() );

        return userDto.build();
    }

    protected OrderDetailsDto orderDetailsToOrderDetailsDto(OrderDetails orderDetails) {
        if ( orderDetails == null ) {
            return null;
        }

        OrderDetailsDtoBuilder orderDetailsDto = OrderDetailsDto.builder();

        orderDetailsDto.id( orderDetails.getId() );
        orderDetailsDto.customerName( orderDetails.getCustomerName() );
        orderDetailsDto.phone( orderDetails.getPhone() );
        orderDetailsDto.city( orderDetails.getCity() );
        orderDetailsDto.country( orderDetails.getCountry() );
        orderDetailsDto.street( orderDetails.getStreet() );
        orderDetailsDto.paymentMode( orderDetails.getPaymentMode() );

        return orderDetailsDto.build();
    }
}
