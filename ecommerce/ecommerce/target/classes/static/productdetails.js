const productDetails =
    document.getElementById("productDetails");

const params =
    new URLSearchParams(window.location.search);

const productId =
    params.get("id");


async function loadProduct() {

    if (!productId) {

        productDetails.innerHTML =
            "<h2>Product ID not found</h2>";

        return;
    }

    const response =
        await fetch("/api/products/" + productId);

    if (!response.ok) {

        productDetails.innerHTML =
            "<h2>Product not found</h2>";

        return;
    }

    const product =
        await response.json();

    productDetails.innerHTML = `

        <div class="product">

            <img
                src="${product.imageUrl}"
                alt="${product.name}">

            <h2>${product.name}</h2>

            <p>
                <strong>Price:</strong>
                ₹${product.price}
            </p>

            <p>
                <strong>Category:</strong>
                ${product.category}
            </p>

            <p>
                <strong>Available Stock:</strong>
                ${product.stock}
            </p>

            <button
                onclick="addToCart(${product.id})">
                Add to Cart
            </button>

        </div>
    `;
}


async function addToCart(productId) {

    const response =
        await fetch("/api/cart", {

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


loadProduct();