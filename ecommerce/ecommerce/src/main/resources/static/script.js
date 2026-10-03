const productsContainer =
    document.getElementById("productsContainer");

const searchInput =
    document.getElementById("searchInput");

const categorySelect =
    document.getElementById("categorySelect");

const sortSelect =
    document.getElementById("sortSelect");


async function loadProducts() {

    let url = "/api/products";

    const search = searchInput.value;
    const category = categorySelect.value;
    const sort = sortSelect.value;

    const params = new URLSearchParams();

    if (search) {
        params.append("search", search);
    }

    if (category) {
        params.append("category", category);
    }

    if (sort) {
        params.append("sort", sort);
    }

    if (params.toString()) {
        url = "/api/products?" + params.toString();
    }

    const response = await fetch(url);

    if (!response.ok) {

        productsContainer.innerHTML =
            "<h2>Unable to load products</h2>";

        return;
    }

    const products = await response.json();

    productsContainer.innerHTML = "";

    if (products.length === 0) {

        productsContainer.innerHTML =
            "<h2>No products found</h2>";

        return;
    }

    products.forEach(product => {

        const div = document.createElement("div");

        div.className = "product";

        div.innerHTML = `

            <img
                src="${product.imageUrl}"
                alt="${product.name}">

            <h3>${product.name}</h3>

            <p>Price: ₹${product.price}</p>

            <p>Category: ${product.category}</p>

            <p>Stock: ${product.stock}</p>

            <button
                onclick="addToCart(${product.id})">
                Add to Cart
            </button>

            <button
                onclick="viewProduct(${product.id})">
                View Details
            </button>
        `;

        productsContainer.appendChild(div);
    });
}


async function addToCart(productId) {

    const response = await fetch("/api/cart", {

        method: "POST",

        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify({
            productId: productId,
            quantity: 1
        })
    });

    if (response.ok) {

        alert("Product added to cart");

    } else {

        const data = await response.json();

        alert(data.error || "Unable to add product");
    }
}


function viewProduct(productId) {

    window.location.href =
        "productdetails.html?id=" + productId;
}


searchInput.addEventListener(
    "input",
    loadProducts
);

categorySelect.addEventListener(
    "change",
    loadProducts
);

sortSelect.addEventListener(
    "change",
    loadProducts
);

loadProducts();