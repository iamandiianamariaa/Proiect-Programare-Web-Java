package com.example.shop.mapper;

import com.example.shop.domain.OrderDetails;
import com.example.shop.domain.OrderDetails.OrderDetailsBuilder;
import com.example.shop.dto.OrderDetailsDto;
import com.example.shop.dto.OrderDetailsDto.OrderDetailsDtoBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-01-11T11:56:47+0200",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.9 (Oracle Corporation)"
)
@Component
public class OrderDetailsMapperImpl implements OrderDetailsMapper {

    @Override
    public OrderDetailsDto mapToDto(OrderDetails orderDetails) {
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

    @Override
    public OrderDetails mapToEntity(OrderDetailsDto orderDetailsDto) {
        if ( orderDetailsDto == null ) {
            return null;
        }

        OrderDetailsBuilder orderDetails = OrderDetails.builder();

        orderDetails.id( orderDetailsDto.getId() );
        orderDetails.customerName( orderDetailsDto.getCustomerName() );
        orderDetails.phone( orderDetailsDto.getPhone() );
        orderDetails.city( orderDetailsDto.getCity() );
        orderDetails.country( orderDetailsDto.getCountry() );
        orderDetails.street( orderDetailsDto.getStreet() );
        orderDetails.paymentMode( orderDetailsDto.getPaymentMode() );

        return orderDetails.build();
    }

    @Override
    public OrderDetails update(OrderDetailsDto orderDetailsDto, OrderDetails orderDetails) {
        if ( orderDetailsDto == null ) {
            return null;
        }

        orderDetails.setCustomerName( orderDetailsDto.getCustomerName() );
        orderDetails.setPhone( orderDetailsDto.getPhone() );
        orderDetails.setCity( orderDetailsDto.getCity() );
        orderDetails.setCountry( orderDetailsDto.getCountry() );
        orderDetails.setStreet( orderDetailsDto.getStreet() );
        orderDetails.setPaymentMode( orderDetailsDto.getPaymentMode() );

        return orderDetails;
    }
}
