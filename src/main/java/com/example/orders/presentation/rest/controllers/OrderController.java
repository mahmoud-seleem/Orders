package com.example.orders.presentation.rest.controllers;

import com.example.orders.core.application.dto.OrderDetailsDto;
import com.example.orders.core.application.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController { // to implement controller interface (and put the swagger doc on it )

    @Autowired
    private OrderService orderService;

    @PostMapping("/new")
    public OrderDetailsDto createNewOrder(@RequestBody OrderDetailsDto orderDetailsDto) throws Exception{
        return orderService.createNewOrder(orderDetailsDto);
    }

    @PutMapping("/update")
    public OrderDetailsDto updateOrder(@RequestBody OrderDetailsDto orderDetailsDto) throws Exception{
        return orderService.updateOrder(orderDetailsDto);
    }

    @GetMapping("/all")
    public List<OrderDetailsDto> getAllOrders() throws Exception{
        return orderService.getAllOrders();
    }

    @GetMapping("/{orderId}")
    public OrderDetailsDto getOrderById(@PathVariable Long orderId) throws Exception{
        return orderService.getOrderById(orderId);
    }

    @DeleteMapping("/{orderId}")
    public OrderDetailsDto deleteOrderById(@PathVariable Long orderId) throws Exception{
        return orderService.deleteOrderById(orderId);
    }
}
