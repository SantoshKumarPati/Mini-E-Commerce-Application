package com.Ecom.ecommerce.controller;

import com.Ecom.ecommerce.dto.CartRequest;
import com.Ecom.ecommerce.dto.CartUpdateRequest;
import com.Ecom.ecommerce.entity.Cart;
import com.Ecom.ecommerce.service.CartService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public Cart addToCart(
            @Valid @RequestBody CartRequest request) {

        return cartService.addToCart(request);
    }

    @GetMapping
    public List<Cart> getCart() {

        return cartService.getCart();
    }

    @PutMapping("/{id}")
    public Cart updateCart(
            @PathVariable Long id,
            @Valid @RequestBody CartUpdateRequest request) {

        return cartService.updateCart(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteCart(
            @PathVariable Long id) {

        cartService.deleteCart(id);
    }
}