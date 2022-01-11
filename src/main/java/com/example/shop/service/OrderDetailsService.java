package com.example.shop.service;

import com.example.shop.domain.OrderDetails;
import com.example.shop.dto.OrderDetailsDto;
import com.example.shop.exception.EntityNotFoundException;
import com.example.shop.mapper.OrderDetailsMapper;
import com.example.shop.repository.OrderDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailsService {
    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Autowired
    private OrderDetailsMapper orderDetailsMapper;

    public OrderDetails create(OrderDetails orderDetails) {
        return orderDetailsRepository.save(orderDetails);
    }

    public OrderDetails getById(Long orderDetailsId) {
        return orderDetailsRepository.findById(orderDetailsId)
                .orElseThrow(() -> EntityNotFoundException.builder()
                        .entityId(orderDetailsId)
                        .entityName("Order Details")
                        .build()
                );
    }

    public OrderDetails update(Long orderDetailsId, OrderDetailsDto orderDetailsDto) {
        OrderDetails orderDetails = getById(orderDetailsId);
        OrderDetails updatedOrderDetails = orderDetailsMapper.update(orderDetailsDto, orderDetails);

        return orderDetailsRepository.save(updatedOrderDetails);
    }

    public List<OrderDetails> getAll() {
        return orderDetailsRepository.findAll();
    }

    public void deleteById(Long id) {
        getById(id);
        orderDetailsRepository.deleteById(id);
    }
}
