package com.Ecom.ecommerce.dto;

import com.Ecom.ecommerce.entity.Order;

public class OrderResponse {

    private Long orderId;
    private double totalAmount;
    private String status;
    private Order order;

    public OrderResponse(Order order) {
        this.order = order;
        this.orderId = order.getId();
        this.totalAmount = order.getTotalAmount();
        this.status = order.getStatus();
    }

    public Long getOrderId() {
        return orderId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public Order getOrder() {
        return order;
    }
}