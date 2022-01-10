package com.example.shop.controller;

import com.example.shop.domain.Order;
import com.example.shop.dto.OrderDto;
import com.example.shop.dto.OrderRequestDto;
import com.example.shop.mapper.OrderMapper;
import com.example.shop.service.OrderService;
import com.example.shop.validator.ValidAdmin;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Validated
@RequestMapping("/orders")
@Api(value = "/orders",
        tags = "Orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderMapper orderMapper;

    @PostMapping
    @Operation(
            method = "POST",
            summary = "Place a new order"
    )
    public ResponseEntity<OrderDto> createOrder(@RequestBody @Valid OrderRequestDto orderRequestDto) {
        return ResponseEntity
                .ok()
                .body(orderService.create(orderRequestDto));
    }

    @GetMapping("/{orderId}")
    @Operation(
            method = "GET",
            summary = "Get order by id"
    )
    public ResponseEntity<OrderDto> getById(@PathVariable Long orderId) {

        OrderDto result = orderMapper.mapToDto(orderService.getById(orderId));

        return ResponseEntity
                .ok()
                .body(result);
    }

    @GetMapping("/{adminId}/all")
    @Operation(
            method = "GET",
            summary = "Get all orders"
    )
    public ResponseEntity<List<OrderDto>> getAll(@PathVariable @ValidAdmin Long adminId) {

        List<OrderDto> result = orderService.getAll()
                .stream().map(orderMapper::mapToDto)
                .collect(Collectors.toList());

        return ResponseEntity
                .ok()
                .body(result);
    }

    @PutMapping("/{adminId}/{orderId}")
    @Operation(
            method = "PUT",
            summary = "Update order by id"
    )
    public ResponseEntity<OrderDto> updateOrder(@PathVariable @ValidAdmin Long adminId,
                                                @PathVariable Long orderId,
                                                @RequestBody @Valid OrderRequestDto orderRequestDto) {
        Order savedOrder = orderService.update(orderId, orderRequestDto);

        return ResponseEntity
                .ok()
                .body(orderMapper.mapToDto(savedOrder));
    }

    @PutMapping("/orderStatus/{adminId}/{orderId}")
    @Operation(
            method = "PUT",
            summary = "Update order status by id"
    )
    public ResponseEntity<OrderDto> updateOrderStatus(@PathVariable @ValidAdmin Long adminId,
                                                      @PathVariable Long orderId,
                                                      @RequestParam(value = "status") String status) {
        Order savedOrder = orderService.updateOrderStatus(orderId, status);

        return ResponseEntity
                .ok()
                .body(orderMapper.mapToDto(savedOrder));
    }

    @DeleteMapping("/{adminId}/{orderId}")
    @Operation(
            method = "DELETE",
            summary = "Cancel order by id"
    )
    public ResponseEntity<?> deleteById(@PathVariable @ValidAdmin Long adminId,
                                        @PathVariable Long orderId) {

        orderService.deleteById(orderId);

        return ResponseEntity
                .noContent()
                .build();
    }

}
