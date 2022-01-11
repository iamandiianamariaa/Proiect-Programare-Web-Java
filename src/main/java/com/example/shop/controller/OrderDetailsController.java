package com.example.shop.controller;

import com.example.shop.domain.OrderDetails;
import com.example.shop.dto.OrderDetailsDto;
import com.example.shop.mapper.OrderDetailsMapper;
import com.example.shop.service.OrderDetailsService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping("/orderDetails")
@Api(value = "/orderDetails",
        tags = "Order Details")
public class OrderDetailsController {
    @Autowired
    private OrderDetailsService orderDetailsService;

    @Autowired
    private final OrderDetailsMapper orderDetailsMapper;

    @Autowired
    public OrderDetailsController(OrderDetailsService orderDetailsService, OrderDetailsMapper orderDetailsMapper) {
        this.orderDetailsService = orderDetailsService;
        this.orderDetailsMapper = orderDetailsMapper;
    }


    @PostMapping("/create")
    @Operation(
            method = "POST",
            summary = "Add order details for order"
    )
    public ResponseEntity<OrderDetailsDto> createOrderDetails(@RequestBody @Valid OrderDetailsDto orderDetailsDto) {
        OrderDetails orderDetails = orderDetailsService.create(orderDetailsMapper.mapToEntity(orderDetailsDto));
        return ResponseEntity
                .ok()
                .body(orderDetailsMapper.mapToDto(orderDetails));
    }

    @GetMapping("/{orderDetailsId}")
    @Operation(
            method = "GET",
            summary = "Get order details by order id"
    )
    public ResponseEntity<OrderDetailsDto> getById(@PathVariable Long orderDetailsId) {

        OrderDetailsDto result = orderDetailsMapper.mapToDto(orderDetailsService.getById(orderDetailsId));

        return ResponseEntity
                .ok()
                .body(result);
    }

    @GetMapping
    @Operation(
            method = "GET",
            summary = "Get all order details"
    )
    public ResponseEntity<List<OrderDetailsDto>> getAll() {

        List<OrderDetailsDto> result = orderDetailsService.getAll()
                .stream().map(orderDetailsMapper::mapToDto)
                .collect(Collectors.toList());

        return ResponseEntity
                .ok()
                .body(result);
    }

    @PutMapping("/{orderDetailsId}")
    @Operation(
            method = "PUT",
            summary = "Update order details by id"
    )
    public ResponseEntity<OrderDetailsDto> updateCategory(@PathVariable Long orderDetailsId,
                                                          @RequestBody @Valid OrderDetailsDto orderDetailsDto) {

        OrderDetails savedOrderDetails = orderDetailsService.update(orderDetailsId, orderDetailsDto);

        return ResponseEntity
                .ok()
                .body(orderDetailsMapper.mapToDto(savedOrderDetails));
    }

    @DeleteMapping("/{orderDetailsId}")
    @Operation(
            method = "DELETE",
            summary = "Delete order details by id"
    )
    public ResponseEntity<?> deleteById(@PathVariable Long orderDetailsId) {

        orderDetailsService.deleteById(orderDetailsId);

        return ResponseEntity
                .noContent()
                .build();
    }

}
