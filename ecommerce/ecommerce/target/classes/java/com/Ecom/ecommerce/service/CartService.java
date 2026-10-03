package com.Ecom.ecommerce.service;

import com.Ecom.ecommerce.dto.CartRequest;
import com.Ecom.ecommerce.dto.CartUpdateRequest;
import com.Ecom.ecommerce.entity.Cart;
import com.Ecom.ecommerce.entity.Product;
import com.Ecom.ecommerce.exception.InsufficientStockException;
import com.Ecom.ecommerce.exception.ResourceNotFoundException;
import com.Ecom.ecommerce.repository.CartRepository;
import com.Ecom.ecommerce.repository.ProductRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartService(
            CartRepository cartRepository,
            ProductRepository productRepository) {

        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public Cart addToCart(CartRequest request) {

        Product product = productRepository
                .findById(request.getProductId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found"));

        if (request.getQuantity() > product.getStock()) {

            throw new InsufficientStockException(
                    "Not enough stock available");
        }

        Cart existingCart =
                cartRepository.findByProductId(
                        request.getProductId());

        if (existingCart != null) {

            int newQuantity =
                    existingCart.getQuantity()
                            + request.getQuantity();

            if (newQuantity > product.getStock()) {

                throw new InsufficientStockException(
                        "Not enough stock available");
            }

            existingCart.setQuantity(newQuantity);

            return cartRepository.save(existingCart);
        }

        Cart cart = new Cart();

        cart.setProduct(product);
        cart.setQuantity(request.getQuantity());

        return cartRepository.save(cart);
    }

    public List<Cart> getCart() {

        return cartRepository.findAll();
    }

    public Cart updateCart(
            Long id,
            CartUpdateRequest request) {

        Cart cart = cartRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cart item not found"));

        Product product = cart.getProduct();

        if (request.getQuantity() > product.getStock()) {

            throw new InsufficientStockException(
                    "Not enough stock available");
        }

        cart.setQuantity(request.getQuantity());

        return cartRepository.save(cart);
    }

    public void deleteCart(Long id) {

        Cart cart = cartRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cart item not found"));

        cartRepository.delete(cart);
    }
}