package com.example.shop.service;

import com.example.shop.domain.Order;
import com.example.shop.domain.Product;
import com.example.shop.domain.enums.Status;
import com.example.shop.dto.OrderDto;
import com.example.shop.dto.OrderRequestDto;
import com.example.shop.exception.EntityNotFoundException;
import com.example.shop.mapper.OrderMapper;
import com.example.shop.repository.OrderRepository;
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
class OrderServiceTest {

    @Mock
    OrderRepository orderRepository;

    @Mock
    OrderMapper orderMapper;

    @InjectMocks
    OrderService orderService;

    @Test
    @DisplayName("Place order")
    void saveOrder() {
        Long id = 1L;
        Double price = 36.7;
        Status status = Status.PLACED;
        Integer noProducts = 1;
        LocalDateTime dateAdded = LocalDateTime.now();
        Long userId = 2L;
        Long orderDetailsId = 3L;
        List<Long> list = List.of(1L);

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

        List<Product> productList = List.of(product);

        OrderRequestDto orderRequestDto = OrderRequestDto.builder()
                .id(id)
                .userId(userId)
                .orderDetailsId(orderDetailsId)
                .productIds(list)
                .build();

        OrderDto orderDto = OrderDto.builder()
                .id(id)
                .price(price)
                .status(status)
                .noProducts(noProducts)
                .orderAddedDate(dateAdded)
                .build();

        Order orderToBeSaved = Order.builder()
                .id(id)
                .price(price)
                .status(status)
                .noProducts(noProducts)
                .products(productList)
                .orderAddedDate(dateAdded)
                .build();

        Order orderSaved = Order.builder()
                .id(id)
                .price(price)
                .status(status)
                .noProducts(noProducts)
                .orderAddedDate(dateAdded)
                .build();

        when(orderRepository.save(orderToBeSaved)).thenReturn(orderSaved);

        when(orderMapper.mapToEntity(orderRequestDto)).thenReturn(orderToBeSaved);
        when(orderMapper.mapToDto(orderSaved)).thenReturn(orderDto);

        OrderDto result = orderService.create(orderRequestDto);

        assertEquals(orderSaved.getId(), result.getId());
        assertEquals(orderSaved.getPrice(), result.getPrice());
        assertEquals(orderSaved.getStatus(), result.getStatus());
        assertEquals(orderSaved.getNoProducts(), result.getNoProducts());
        assertEquals(orderSaved.getOrderAddedDate(), result.getOrderAddedDate());
    }

    @Test
    @DisplayName("Get order by ID successful")
    void getOrderById() {
        Long id = 1L;
        Order order = Order.builder()
                .id(id)
                .price(35.3)
                .status(Status.PLACED)
                .noProducts(1)
                .orderAddedDate(LocalDateTime.now())
                .build();


        when(orderRepository.findById(id)).thenReturn(Optional.of(order));

        Order result = orderService.getById(id);

        assertEquals(order, result);
    }

    @Test
    @DisplayName("Get order by ID FAILED")
    void getOrderByIdFailed() {
        Long id = 1L;

        when(orderRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> orderService.getById(id));
    }

    @Test
    @DisplayName("Update order")
    void updateOrder() {
        Long id = 1L;
        Double price = 36.7;
        Status status = Status.PLACED;
        Integer noProducts = 1;
        LocalDateTime dateAdded = LocalDateTime.now();
        Long userId = 2L;
        Long orderDetailsId = 3L;
        List<Long> list = List.of(1L);

        Double priceUpdated = 44.3;


        OrderRequestDto orderRequestDto = OrderRequestDto.builder()
                .id(id)
                .userId(userId)
                .orderDetailsId(orderDetailsId)
                .productIds(list)
                .build();

        Order orderToBeUpdated = Order.builder()
                .id(id)
                .price(price)
                .status(status)
                .noProducts(noProducts)
                .orderAddedDate(dateAdded)
                .build();

        Order orderUpdated = Order.builder()
                .id(id)
                .price(priceUpdated)
                .status(status)
                .noProducts(noProducts)
                .orderAddedDate(dateAdded)
                .build();

        Order orderSaved = Order.builder()
                .id(id)
                .price(priceUpdated)
                .status(status)
                .noProducts(noProducts)
                .orderAddedDate(dateAdded)
                .build();


        when(orderRepository.findById(id)).thenReturn(Optional.of(orderToBeUpdated));
        when(orderMapper.update(orderRequestDto, orderToBeUpdated)).thenReturn(orderUpdated);

        when(orderRepository.save(any())).thenReturn(orderSaved);

        Order result = orderService.update(id, orderRequestDto);

        assertEquals(orderSaved, result);
    }

    @Test
    @DisplayName("Update order status")
    void updateOrderStatus() {
        Long id = 1L;
        Double price = 36.7;
        Status status = Status.PLACED;
        Integer noProducts = 1;
        LocalDateTime dateAdded = LocalDateTime.now();

        Status statusUpdated = Status.PACKED;

        Order order = Order.builder()
                .id(id)
                .price(price)
                .status(status)
                .noProducts(noProducts)
                .orderAddedDate(dateAdded)
                .build();

        Order orderSaved = Order.builder()
                .id(id)
                .price(price)
                .status(statusUpdated)
                .noProducts(noProducts)
                .orderAddedDate(dateAdded)
                .build();


        when(orderRepository.findById(id)).thenReturn(Optional.of(order));

        when(orderRepository.save(any())).thenReturn(orderSaved);

        Order result = orderService.updateOrderStatus(id, String.valueOf(statusUpdated));

        assertEquals(orderSaved, result);
    }

    @Test
    @DisplayName("Get all orders")
    void getAllOrders() {
        Long id = 1L;
        Order order1 = Order.builder()
                .id(id)
                .price(354.3)
                .status(Status.PLACED)
                .noProducts(5)
                .orderAddedDate(LocalDateTime.now())
                .build();

        Order order2 = Order.builder()
                .id(id)
                .price(35.3)
                .status(Status.PLACED)
                .noProducts(1)
                .orderAddedDate(LocalDateTime.now())
                .build();

        when(orderRepository.findAll()).thenReturn(Arrays.asList(order1, order2));

        List<Order> result = orderService.getAll();

        assertEquals(result.get(0), order1);
        assertEquals(result.get(1), order2);
    }

    @Test
    @DisplayName("Delete order")
    void deleteOrder() {
        Long id = 1L;

        doNothing().when(orderRepository).deleteById(id);

        orderService.deleteById(id);

        verify(orderRepository, times(1)).deleteById(id);
    }
}