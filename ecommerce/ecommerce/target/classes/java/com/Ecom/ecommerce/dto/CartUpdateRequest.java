package com.Ecom.ecommerce.dto;

import jakarta.validation.constraints.Min;

public class CartUpdateRequest {

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

    public CartUpdateRequest() {
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}