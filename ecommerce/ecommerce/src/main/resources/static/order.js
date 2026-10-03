const orderDetails =
    document.getElementById("orderDetails");

const params =
    new URLSearchParams(window.location.search);

const orderId =
    params.get("id");


async function loadOrder() {

    if (!orderId) {

        orderDetails.innerHTML =
            "<h2>Order ID not found</h2>";

        return;
    }

    const response =
        await fetch(
            "/api/orders/" + orderId
        );

    if (!response.ok) {

        orderDetails.innerHTML =
            "<h2>Order not found</h2>";

        return;
    }

    const order =
        await response.json();

    let itemsHTML = "";

    order.orderItems.forEach(item => {

        itemsHTML += `

            <div class="product">

                <h3>
                    ${item.productName}
                </h3>

                <p>
                    Price: ₹${item.price}
                </p>

                <p>
                    Quantity: ${item.quantity}
                </p>

                <p>
                    Subtotal: ₹${item.subtotal}
                </p>

            </div>
        `;
    });


    orderDetails.innerHTML = `

        <div class="product">

            <h2>
                Thank You for Your Order!
            </h2>

            <p>
                <strong>Order ID:</strong>
                ${order.id}
            </p>

            <p>
                <strong>Status:</strong>
                ${order.status}
            </p>

            <p>
                <strong>Name:</strong>
                ${order.customerName}
            </p>

            <p>
                <strong>Email:</strong>
                ${order.email}
            </p>

            <p>
                <strong>Phone:</strong>
                ${order.phone}
            </p>

            <p>
                <strong>Address:</strong>
                ${order.address}
            </p>

            <h2>Order Items</h2>

            ${itemsHTML}

            <h2>
                Total Amount:
                ₹${order.totalAmount}
            </h2>

            <button onclick="goToHome()">
                Continue Shopping
            </button>

        </div>
    `;
}


function goToHome() {

    window.location.href =
        "index.html";
}


loadOrder();