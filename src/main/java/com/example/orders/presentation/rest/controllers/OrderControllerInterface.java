package com.example.orders.presentation.rest.controllers;


import com.example.orders.core.application.dto.OrderDetailsDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface OrderControllerInterface {

    @PostMapping("/new")
    OrderDetailsDto createNewOrder(@RequestBody OrderDetailsDto orderDetailsDto) throws Exception;

    @PutMapping("/update")
    OrderDetailsDto updateOrder(@RequestBody OrderDetailsDto orderDetailsDto) throws Exception;

    @GetMapping("/all")
    List<OrderDetailsDto> getAllOrders() throws Exception;

    @GetMapping("/{orderId}")
    OrderDetailsDto getOrderById(@PathVariable Long orderId) throws Exception;

    @DeleteMapping("/{orderId}")
    OrderDetailsDto deleteOrderById(@PathVariable Long orderId) throws Exception;
}
