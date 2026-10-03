# E-Commerce Web Application

A simple full-stack E-Commerce web application built using **Java, Spring Boot, MySQL, HTML, CSS, and JavaScript**.

The application allows users to browse products, search and filter products, view product details, add products to a cart, update cart quantities, checkout, and place orders.

---

## Features

### Product Management

- Display all products
- Product name
- Product price
- Product category
- Available stock
- Product image
- Search products by name
- Filter products by category
- Sort products by price
  - Low to High
  - High to Low
- View product details
- Add new products
- Update products
- Delete products

### Shopping Cart

- Add products to cart
- View cart items
- Increase product quantity
- Decrease product quantity
- Remove products from cart
- Check product stock before increasing quantity
- Automatically calculate product subtotal
- Automatically calculate cart total

### Checkout

The checkout page collects:

- Customer Name
- Email
- Phone Number
- Address

The application validates the checkout information before placing the order.

### Order Management

- Place an order
- Validate product stock before placing an order
- Automatically reduce product stock after successful order
- Store order information in MySQL
- Store order items in MySQL
- Generate an Order ID
- Display Order Summary
- Display total order amount
- View order details

---

## Technologies Used

### Backend

- Java 23
- Spring Boot 3.5.6
- Spring Web
- Spring Data JPA
- Hibernate
- Jakarta Validation
- MySQL

### Frontend

- HTML5
- CSS3
- JavaScript
- Fetch API

### Tools

- Eclipse / Visual Studio Code
- MySQL
- MySQL Workbench
- Postman
- Maven

---

## Project Structure

```text
ecommerce
│
├── src
│   └── main
│       ├── java
│       │   └── com
│       │       └── Ecom
│       │           └── ecommerce
│       │               │
│       │               ├── EcommerceApplication.java
│       │               │
│       │               ├── controller
│       │               │   ├── ProductController.java
│       │               │   ├── CartController.java
│       │               │   └── OrderController.java
│       │               │
│       │               ├── dto
│       │               │   ├── ProductRequest.java
│       │               │   ├── CartRequest.java
│       │               │   ├── CartUpdateRequest.java
│       │               │   ├── CheckoutRequest.java
│       │               │   └── OrderResponse.java
│       │               │
│       │               ├── entity
│       │               │   ├── Product.java
│       │               │   ├── Cart.java
│       │               │   ├── Order.java
│       │               │   └── OrderItem.java
│       │               │
│       │               ├── repository
│       │               │   ├── ProductRepository.java
│       │               │   ├── CartRepository.java
│       │               │   ├── OrderRepository.java
│       │               │   └── OrderItemRepository.java
│       │               │
│       │               ├── service
│       │               │   ├── ProductService.java
│       │               │   ├── CartService.java
│       │               │   └── OrderService.java
│       │               │
│       │               └── exception
│       │                   ├── ResourceNotFoundException.java
│       │                   ├── InsufficientStockException.java
│       │                   └── GlobalExceptionHandler.java
│       │
│       └── resources
│           │
│           ├── static
│           │   ├── index.html
│           │   ├── productdetails.html
│           │   ├── productdetails.js
│           │   ├── cart.html
│           │   ├── cart.js
│           │   ├── checkout.html
│           │   ├── checkout.js
│           │   ├── order.html
│           │   ├── order.js
│           │   ├── script.js
│           │   ├── style.css
│           │   │
│           │   └── images
│           │       ├── laptop.jpg
│           │       ├── phone.jpg
│           │       ├── tshirt.jpg
│           │       ├── book.jpg
│           │       ├── laptop3.jpg
│           │       ├── phone1.jpg
│           │       ├── shirt.jpg
│           │       └── book1.jpg
│           │
│           └── application.properties
│
└── pom.xml