const checkoutForm =
    document.getElementById("checkoutForm");

const errorMessage =
    document.getElementById("errorMessage");


checkoutForm.addEventListener(
    "submit",
    async function(event) {

        event.preventDefault();

        const name =
            document.getElementById("name").value;

        const email =
            document.getElementById("email").value;

        const phone =
            document.getElementById("phone").value;

        const address =
            document.getElementById("address").value;


        const orderData = {
            name: name,
            email: email,
            phone: phone,
            address: address
        };


        try {

            const response =
                await fetch(
                    "/api/orders",
                    {
                        method: "POST",

                        headers: {
                            "Content-Type":
                                "application/json"
                        },

                        body:
                            JSON.stringify(orderData)
                    }
                );


            const data =
                await response.json();


            if (response.ok) {

                window.location.href =
                    "order-success.html?id="
                    + data.orderId;

            } else {

                errorMessage.textContent =
                    data.error ||
                    "Unable to place order";
            }

        } catch (error) {

            errorMessage.textContent =
                "Server error. Please try again.";
        }
    }
);