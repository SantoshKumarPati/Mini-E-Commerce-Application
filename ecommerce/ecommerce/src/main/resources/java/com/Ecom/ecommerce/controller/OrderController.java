package com.Ecom.ecommerce.controller;

import com.Ecom.ecommerce.dto.CheckoutRequest;
import com.Ecom.ecommerce.dto.OrderResponse;
import com.Ecom.ecommerce.entity.Order;
import com.Ecom.ecommerce.service.OrderService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public OrderResponse placeOrder(
            @Valid @RequestBody CheckoutRequest request) {

        return orderService.placeOrder(request);
    }

    @GetMapping
    public List<Order> getOrders() {

        return orderService.getOrders();
    }

    @GetMapping("/{id}")
    public Order getOrderById(
            @PathVariable Long id) {

        return orderService.getOrderById(id);
    }
}