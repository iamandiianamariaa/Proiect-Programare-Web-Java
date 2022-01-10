package com.example.shop.service;

import com.example.shop.domain.OrderDetails;
import com.example.shop.domain.enums.PaymentMode;
import com.example.shop.dto.OrderDetailsDto;
import com.example.shop.exception.EntityNotFoundException;
import com.example.shop.mapper.OrderDetailsMapper;
import com.example.shop.repository.OrderDetailsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderDetailsServiceTest {

    @Mock
    OrderDetailsRepository orderDetailsRepository;

    @Mock
    OrderDetailsMapper orderDetailsMapper;

    @InjectMocks
    OrderDetailsService orderDetailsService;

    @Test
    @DisplayName("Save order details")
    void saveOrderDetails() {
        Long id = 1L;
        String customerName = "ana";
        String phone = "0748577689";
        String city = "Bucuresti";
        String country = "Romania";
        String street = "Str. Lalelelor";
        PaymentMode paymentMode = PaymentMode.CASH;

        OrderDetails orderDetailsToBeSaved = OrderDetails.builder()
                .id(id)
                .customerName(customerName)
                .phone(phone)
                .city(city)
                .country(country)
                .street(street)
                .paymentMode(paymentMode)
                .build();

        OrderDetails orderDetailsSaved = OrderDetails.builder()
                .id(id)
                .customerName(customerName)
                .phone(phone)
                .city(city)
                .country(country)
                .street(street)
                .paymentMode(paymentMode)
                .build();

        when(orderDetailsRepository.save(orderDetailsToBeSaved)).thenReturn(orderDetailsSaved);

        OrderDetails result = orderDetailsService.create(orderDetailsToBeSaved);

        assertEquals(orderDetailsSaved.getId(), result.getId());
        assertEquals(orderDetailsSaved.getCustomerName(), result.getCustomerName());
        assertEquals(orderDetailsSaved.getPhone(), result.getPhone());
        assertEquals(orderDetailsSaved.getCity(), result.getCity());
        assertEquals(orderDetailsSaved.getCountry(), result.getCountry());
        assertEquals(orderDetailsSaved.getStreet(), result.getStreet());
        assertEquals(orderDetailsSaved.getPaymentMode(), result.getPaymentMode());
    }

    @Test
    @DisplayName("Get order details by ID successful")
    void getOrderDetailsById() {
        Long id = 1L;
        String customerName = "ana";
        String phone = "0748577689";
        String city = "Bucuresti";
        String country = "Romania";
        String street = "Str. Lalelelor";
        PaymentMode paymentMode = PaymentMode.CASH;

        OrderDetails orderDetails = OrderDetails.builder()
                .id(id)
                .customerName(customerName)
                .phone(phone)
                .city(city)
                .country(country)
                .street(street)
                .paymentMode(paymentMode)
                .build();


        when(orderDetailsRepository.findById(id)).thenReturn(Optional.of(orderDetails));

        OrderDetails result = orderDetailsService.getById(id);

        assertEquals(orderDetails, result);
    }

    @Test
    @DisplayName("Get order details by ID FAILED")
    void getOrderDetailsByIdFailed() {
        Long id = 1L;

        when(orderDetailsRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> orderDetailsService.getById(id));
    }

    @Test
    @DisplayName("Update order details")
    void updateOrderDetails() {
        Long id = 1L;
        String customerName = "ana";
        String phone = "0748577689";
        String city = "Bucuresti";
        String country = "Romania";
        String street = "Str. Lalelelor";
        PaymentMode paymentMode = PaymentMode.CASH;

        String updatedCustomerName = "mihaela";


        OrderDetailsDto orderDetailsDto = new OrderDetailsDto();
        orderDetailsDto.setCustomerName(updatedCustomerName);
        orderDetailsDto.setPhone(phone);
        orderDetailsDto.setCity(city);
        orderDetailsDto.setCountry(country);
        orderDetailsDto.setStreet(street);
        orderDetailsDto.setPaymentMode(paymentMode);


        OrderDetails orderDetailsToBeUpdated = OrderDetails.builder()
                .id(id)
                .customerName(customerName)
                .phone(phone)
                .city(city)
                .country(country)
                .street(street)
                .paymentMode(paymentMode)
                .build();

        OrderDetails orderDetailsUpdated = OrderDetails.builder()
                .id(id)
                .customerName(updatedCustomerName)
                .phone(phone)
                .city(city)
                .country(country)
                .street(street)
                .paymentMode(paymentMode)
                .build();

        OrderDetails orderDetailsSaved = OrderDetails.builder()
                .id(id)
                .customerName(updatedCustomerName)
                .phone(phone)
                .city(city)
                .country(country)
                .street(street)
                .paymentMode(paymentMode)
                .build();


        when(orderDetailsRepository.findById(id)).thenReturn(Optional.of(orderDetailsToBeUpdated));
        when(orderDetailsMapper.update(orderDetailsDto, orderDetailsToBeUpdated)).thenReturn(orderDetailsUpdated);

        when(orderDetailsRepository.save(any())).thenReturn(orderDetailsSaved);

        OrderDetails result = orderDetailsService.update(id, orderDetailsDto);

        assertEquals(orderDetailsSaved, result);
    }

    @Test
    @DisplayName("Get all order details")
    void getAllOrderDetails() {

        OrderDetails orderDetails1 = OrderDetails.builder()
                .id(1L)
                .customerName("ana")
                .phone("04683")
                .city("Bucuresti")
                .country("Romania")
                .street("Str. Lalelelor")
                .paymentMode(PaymentMode.CASH)
                .build();

        OrderDetails orderDetails2 = OrderDetails.builder()
                .id(2L)
                .customerName("maria")
                .phone("748844")
                .city("Bucuresti")
                .country("Romania")
                .street("Str. Castanelor")
                .paymentMode(PaymentMode.CARD)
                .build();

        when(orderDetailsRepository.findAll()).thenReturn(Arrays.asList(orderDetails1, orderDetails2));

        List<OrderDetails> result = orderDetailsService.getAll();

        assertEquals(result.get(0), orderDetails1);
        assertEquals(result.get(1), orderDetails2);
    }

    @Test
    @DisplayName("Delete order details")
    void deleteOrderDetails() {
        Long id = 1L;

        doNothing().when(orderDetailsRepository).deleteById(id);

        orderDetailsService.deleteById(id);

        verify(orderDetailsRepository, times(1)).deleteById(id);
    }
}