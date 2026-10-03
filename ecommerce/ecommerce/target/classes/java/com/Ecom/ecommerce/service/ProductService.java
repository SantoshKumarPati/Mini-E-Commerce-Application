package com.Ecom.ecommerce.service;

import com.Ecom.ecommerce.dto.ProductRequest;
import com.Ecom.ecommerce.entity.Product;
import com.Ecom.ecommerce.exception.ResourceNotFoundException;
import com.Ecom.ecommerce.repository.ProductRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts(
            String search,
            String category,
            String sort) {

        List<Product> products =
                new ArrayList<>(productRepository.findAll());

        if (search != null && !search.isBlank()) {

            String searchText = search.toLowerCase();

            products.removeIf(product ->
                    !product.getName()
                            .toLowerCase()
                            .contains(searchText));
        }

        if (category != null && !category.isBlank()) {

            products.removeIf(product ->
                    !product.getCategory()
                            .equalsIgnoreCase(category));
        }

        if ("asc".equalsIgnoreCase(sort)) {

            products.sort(
                    Comparator.comparingDouble(Product::getPrice));
        }

        if ("desc".equalsIgnoreCase(sort)) {

            products.sort(
                    Comparator.comparingDouble(Product::getPrice)
                            .reversed());
        }

        return products;
    }

    public Product getProductById(Long id) {

        return productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found with id: " + id));
    }

    public Product createProduct(ProductRequest request) {

        Product product = new Product();

        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setCategory(request.getCategory());
        product.setStock(request.getStock());
        product.setImageUrl(request.getImageUrl());

        return productRepository.save(product);
    }

    public Product updateProduct(
            Long id,
            ProductRequest request) {

        Product product = getProductById(id);

        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setCategory(request.getCategory());
        product.setStock(request.getStock());
        product.setImageUrl(request.getImageUrl());

        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {

        Product product = getProductById(id);

        productRepository.delete(product);
    }
}