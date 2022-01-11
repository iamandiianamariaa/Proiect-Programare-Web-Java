package com.example.shop.controller;

import com.example.shop.domain.OrderDetails;
import com.example.shop.domain.enums.PaymentMode;
import com.example.shop.dto.OrderDetailsDto;
import com.example.shop.mapper.OrderDetailsMapper;
import com.example.shop.service.OrderDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OrderDetailsController.class)
class OrderDetailsControllerTest {

    @MockBean
    private OrderDetailsService orderDetailsService;

    @MockBean
    private OrderDetailsMapper orderDetailsMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createOrderDetails() throws Exception {
        OrderDetails orderDetails = getOrderDetails();
        OrderDetailsDto orderDetailsDto = getOrderDetailsDto();

        when(orderDetailsMapper.mapToEntity(orderDetailsDto)).thenReturn(orderDetails);
        when(orderDetailsMapper.mapToDto(orderDetails)).thenReturn(orderDetailsDto);
        when(orderDetailsService.create(any())).thenReturn(orderDetails);

        MvcResult result = mockMvc.perform(post("/orderDetails/create")
                        .content(objectMapper.writeValueAsString(orderDetailsDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(orderDetailsDto));
    }

    @Test
    void getById() throws Exception {
        Long id = 1L;
        OrderDetails orderDetails = getOrderDetails();
        OrderDetailsDto orderDetailsDto = getOrderDetailsDto();

        when(orderDetailsMapper.mapToDto(orderDetails)).thenReturn(orderDetailsDto);
        when(orderDetailsService.getById(any())).thenReturn(orderDetails);

        mockMvc.perform(get("/orderDetails/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void getAll() throws Exception {
        OrderDetails orderDetails = getOrderDetails();
        OrderDetailsDto orderDetailsDto = getOrderDetailsDto();
        List<OrderDetails> dto = List.of(orderDetails);

        when(orderDetailsMapper.mapToDto(orderDetails)).thenReturn(orderDetailsDto);
        when(orderDetailsService.getAll()).thenReturn(dto);

        mockMvc.perform(get("/orderDetails"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void updateOrderDetails() throws Exception {
        Long id = 1L;
        OrderDetails orderDetails = getOrderDetails();
        OrderDetailsDto orderDetailsDto = getOrderDetailsDto();

        when(orderDetailsService.update(any(), any())).thenReturn(orderDetails);
        when(orderDetailsMapper.mapToDto(orderDetails)).thenReturn(orderDetailsDto);

        mockMvc.perform(put("/orderDetails/" + id)
                        .content(objectMapper.writeValueAsString(orderDetailsDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void deleteById() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/orderDetails/" + id))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    OrderDetails getOrderDetails(){
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

        return orderDetails;
    }

    OrderDetailsDto getOrderDetailsDto(){
        Long id = 1L;
        String customerName = "ana";
        String phone = "0748577689";
        String city = "Bucuresti";
        String country = "Romania";
        String street = "Str. Lalelelor";
        PaymentMode paymentMode = PaymentMode.CASH;

        OrderDetailsDto orderDetailsDto = OrderDetailsDto.builder()
                .id(id)
                .customerName(customerName)
                .phone(phone)
                .city(city)
                .country(country)
                .street(street)
                .paymentMode(paymentMode)
                .build();
        return orderDetailsDto;
    }
}