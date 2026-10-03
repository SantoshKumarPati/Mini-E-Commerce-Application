const cartContainer =
    document.getElementById("cartContainer");

const cartTotal =
    document.getElementById("cartTotal");


async function loadCart() {

    const response =
        await fetch("/api/cart");

    if (!response.ok) {

        cartContainer.innerHTML =
            "<h2>Unable to load cart</h2>";

        return;
    }

    const cartItems =
        await response.json();

    cartContainer.innerHTML = "";

    if (cartItems.length === 0) {

        cartContainer.innerHTML =
            "<h2>Your cart is empty</h2>";

        cartTotal.textContent = "0";

        return;
    }

    let total = 0;

    cartItems.forEach(item => {

        const product = item.product;

        const subtotal =
            product.price * item.quantity;

        total = total + subtotal;

        const div =
            document.createElement("div");

        div.className = "product";

        div.innerHTML = `

            <img
                src="${product.imageUrl}"
                alt="${product.name}">

            <h2>${product.name}</h2>

            <p>Price: ₹${product.price}</p>

            <p>Quantity: ${item.quantity}</p>

            <p>Subtotal: ₹${subtotal}</p>

            <button
                onclick="decreaseQuantity(
                    ${item.id},
                    ${item.quantity})">
                -
            </button>

            <button
                onclick="increaseQuantity(
                    ${item.id},
                    ${item.quantity},
                    ${product.stock})">
                +
            </button>

            <button
                onclick="removeFromCart(${item.id})">
                Remove
            </button>
        `;

        cartContainer.appendChild(div);
    });

    cartTotal.textContent = total;
}


async function increaseQuantity(
    cartId,
    quantity,
    stock) {

    if (quantity >= stock) {

        alert("No more stock available");

        return;
    }

    await updateQuantity(
        cartId,
        quantity + 1);
}


async function decreaseQuantity(
    cartId,
    quantity) {

    if (quantity <= 1) {

        alert("Quantity cannot be less than 1");

        return;
    }

    await updateQuantity(
        cartId,
        quantity - 1);
}


async function updateQuantity(
    cartId,
    quantity) {

    const response =
        await fetch(
            "/api/cart/" + cartId,
            {
                method: "PUT",

                headers: {
                    "Content-Type":
                        "application/json"
                },

                body: JSON.stringify({
                    quantity: quantity
                })
            }
        );

    if (response.ok) {

        loadCart();

    } else {

        const data =
            await response.json();

        alert(
            data.error ||
            "Unable to update quantity"
        );
    }
}


async function removeFromCart(cartId) {

    const response =
        await fetch(
            "/api/cart/" + cartId,
            {
                method: "DELETE"
            }
        );

    if (response.ok) {

        loadCart();

    } else {

        alert("Unable to remove product");
    }
}


function goToCheckout() {

    window.location.href =
        "checkout.html";
}


loadCart();