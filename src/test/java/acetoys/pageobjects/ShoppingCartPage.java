package acetoys.pageobjects;

import io.gatling.javaapi.core.ChainBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

public class ShoppingCartPage {

    public static ChainBuilder viewCart =
            // Conditional logic, if the user is not logged in, log in first
            doIf(session -> !session.getBoolean("customerLoggedIn"))
                    .then(exec(Customer.login))
                    .exec(
                            http("View Cart")
                                    .get("/cart/view")
                                    .check(css("#CategoryHeader").is("Cart Overview"))
                    );

    public static ChainBuilder checkoutCart =
            exec(
                    http("Checkout")
                            .get("/cart/checkout")
            );

    public static ChainBuilder increaseQuantityInCart =
            exec(
                    http("Increase Product Quantity in Cart")
                            .get("/cart/add/19?cartPage=true")
            );
}
