package com.example.shop.service;

import com.example.shop.domain.Order;
import com.example.shop.domain.Product;
import com.example.shop.domain.enums.Status;
import com.example.shop.dto.OrderDto;
import com.example.shop.dto.OrderRequestDto;
import com.example.shop.exception.EntityNotFoundException;
import com.example.shop.mapper.OrderMapper;
import com.example.shop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    public OrderDto create(OrderRequestDto orderRequestDto) {
        Order order = orderMapper.mapToEntity(orderRequestDto);
        order.setStatus(Status.PLACED);
        order.setOrderAddedDate(LocalDateTime.now());
        order.setNoProducts(orderRequestDto.getProductIds().size());
        Double sum = order.getProducts().stream().mapToDouble(Product::getPrice).sum();
        order.setPrice(sum);
        Order savedOrder = orderRepository.save(order);

        return orderMapper.mapToDto(savedOrder);
    }

    public Order getById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> EntityNotFoundException.builder()
                        .entityId(orderId)
                        .entityName("Order")
                        .build()
                );
    }

    public Order update(Long orderId, OrderRequestDto orderRequestDto) {
        Order order = getById(orderId);
        Order updatedOrder = orderMapper.update(orderRequestDto, order);
        updatedOrder.setNoProducts(orderRequestDto.getProductIds().size());

        return orderRepository.save(updatedOrder);
    }

    public Order updateOrderStatus(Long orderId, String status) {
        Order order = getById(orderId);
        order.setStatus(Status.valueOf(status));

        return orderRepository.save(order);
    }

    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    public void deleteById(Long id) {
        getById(id);
        orderRepository.deleteById(id);
    }
}
