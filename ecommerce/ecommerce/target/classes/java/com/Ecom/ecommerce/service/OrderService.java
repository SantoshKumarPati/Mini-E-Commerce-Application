package com.Ecom.ecommerce.service;

import com.Ecom.ecommerce.dto.CheckoutRequest;
import com.Ecom.ecommerce.dto.OrderResponse;
import com.Ecom.ecommerce.entity.Cart;
import com.Ecom.ecommerce.entity.Order;
import com.Ecom.ecommerce.entity.OrderItem;
import com.Ecom.ecommerce.entity.Product;
import com.Ecom.ecommerce.exception.InsufficientStockException;
import com.Ecom.ecommerce.exception.ResourceNotFoundException;
import com.Ecom.ecommerce.repository.CartRepository;
import com.Ecom.ecommerce.repository.OrderRepository;
import com.Ecom.ecommerce.repository.ProductRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public OrderService(
            OrderRepository orderRepository,
            CartRepository cartRepository,
            ProductRepository productRepository) {

        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public OrderResponse placeOrder(CheckoutRequest request) {

        List<Cart> cartItems =
                cartRepository.findAll();

        if (cartItems.isEmpty()) {

            throw new ResourceNotFoundException(
                    "Cart is empty");
        }

        Order order = new Order();

        order.setCustomerName(request.getName());
        order.setEmail(request.getEmail());
        order.setPhone(request.getPhone());
        order.setAddress(request.getAddress());
        order.setStatus("PLACED");
        order.setOrderDate(LocalDateTime.now());

        double total = 0;

        for (Cart cart : cartItems) {

            Product product = cart.getProduct();

            if (cart.getQuantity() > product.getStock()) {

                throw new InsufficientStockException(
                        "Not enough stock for product: "
                                + product.getName());
            }

            double subtotal =
                    product.getPrice()
                            * cart.getQuantity();

            OrderItem orderItem = new OrderItem();

            orderItem.setProductId(product.getId());
            orderItem.setProductName(product.getName());
            orderItem.setPrice(product.getPrice());
            orderItem.setQuantity(cart.getQuantity());
            orderItem.setSubtotal(subtotal);

            order.getOrderItems().add(orderItem);

            total = total + subtotal;

            product.setStock(
                    product.getStock()
                            - cart.getQuantity());

            productRepository.save(product);
        }

        order.setTotalAmount(total);

        Order savedOrder =
                orderRepository.save(order);

        cartRepository.deleteAll(cartItems);

        return new OrderResponse(savedOrder);
    }

    public List<Order> getOrders() {

        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {

        return orderRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Order not found with id: " + id));
    }
}