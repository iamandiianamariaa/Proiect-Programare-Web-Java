package com.example.shop.controller;

import com.example.shop.domain.Order;
import com.example.shop.domain.enums.Status;
import com.example.shop.dto.OrderDto;
import com.example.shop.dto.OrderRequestDto;
import com.example.shop.mapper.OrderMapper;
import com.example.shop.service.OrderService;
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

@WebMvcTest(controllers = OrderController.class)
class OrderControllerTest {
    private static final Long ADMIN_ID = 2L;

    @MockBean
    private OrderService orderService;

    @MockBean
    private OrderMapper orderMapper;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createOrder() throws Exception {
        OrderDto orderDto = getOrderDto();
        OrderRequestDto orderRequestDto = getOrderRequestDto();

        when(orderService.create(any())).thenReturn(orderDto);

        MvcResult result = mockMvc.perform(post("/orders/")
                        .content(objectMapper.writeValueAsString(orderRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(orderDto));
    }

    @Test
    void getById() throws Exception {
        Long id = 1L;
        OrderDto orderDto = getOrderDto();
        Order order = getOrder();

        when(orderMapper.mapToDto(order)).thenReturn(orderDto);
        when(orderService.getById(any())).thenReturn(order);

        mockMvc.perform(get("/orders/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void getAll() throws Exception {
        OrderDto orderDto = getOrderDto();
        Order order = getOrder();
        List<Order> dto = List.of(order);

        when(orderMapper.mapToDto(order)).thenReturn(orderDto);
        when(userService.checkIfUserHasAdminRole(ADMIN_ID)).thenReturn(Boolean.TRUE);
        when(orderService.getAll()).thenReturn(dto);

        mockMvc.perform(get("/orders/" + ADMIN_ID + "/" + "all"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void getAllForUserId() throws Exception {
        Long id = 1L;
        OrderDto orderDto = getOrderDto();
        Order order = getOrder();
        List<Order> dto = List.of(order);

        when(orderMapper.mapToDto(order)).thenReturn(orderDto);
        when(orderService.getAllOrdersByUserId(id)).thenReturn(dto);

        mockMvc.perform(get("/orders/" + "all/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void updateOrder() throws Exception {
        Long id = 1L;

        OrderDto orderDto = getOrderDto();
        Order order = getOrder();
        OrderRequestDto orderRequestDto = getOrderRequestDto();

        when(orderService.update(any(), any())).thenReturn(order);
        when(orderMapper.mapToDto(order)).thenReturn(orderDto);
        when(userService.checkIfUserHasAdminRole(ADMIN_ID)).thenReturn(Boolean.TRUE);

        mockMvc.perform(put("/orders/" + ADMIN_ID + "/" + id)
                        .content(objectMapper.writeValueAsString(orderRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void updateOrderStatus() throws Exception {
        Long id = 1L;
        OrderDto orderDto = getOrderDto();
        Order order = getOrder();

        Status updatedStatus = Status.SHIPPED;

        when(orderService.update(any(), any())).thenReturn(order);
        when(orderMapper.mapToDto(order)).thenReturn(orderDto);
        when(userService.checkIfUserHasAdminRole(ADMIN_ID)).thenReturn(Boolean.TRUE);

        mockMvc.perform(put("/orders/orderStatus/" + ADMIN_ID + "/" + id)
                        .param("status", String.valueOf(updatedStatus)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void deleteById() throws Exception {
        Long id = 1L;

        when(userService.checkIfUserHasAdminRole(ADMIN_ID)).thenReturn(Boolean.TRUE);

        mockMvc.perform(delete("/orders/" + ADMIN_ID + "/" + id))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    Order getOrder(){
        Long id = 1L;
        Double price = 36.7;
        Status status = Status.PLACED;
        Integer noProducts = 1;
        LocalDateTime dateAdded = LocalDateTime.now();

        Order order = Order.builder()
                .id(id)
                .price(price)
                .status(status)
                .noProducts(noProducts)
                .orderAddedDate(dateAdded)
                .build();

        return order;
    }

    OrderDto getOrderDto(){
        Long id = 1L;
        Double price = 36.7;
        Status status = Status.PLACED;
        Integer noProducts = 1;
        LocalDateTime dateAdded = LocalDateTime.now();

        OrderDto orderDto = OrderDto.builder()
                .id(id)
                .price(price)
                .status(status)
                .noProducts(noProducts)
                .orderAddedDate(dateAdded)
                .build();
        return orderDto;
    }

    OrderRequestDto getOrderRequestDto(){
        Long id = 1L;
        Long userId = 2L;
        Long orderDetailsId = 3L;
        List<Long> list = List.of(1L);

        OrderRequestDto orderRequestDto = OrderRequestDto.builder()
                .id(id)
                .userId(userId)
                .orderDetailsId(orderDetailsId)
                .productIds(list)
                .build();

        return orderRequestDto;
    }
}