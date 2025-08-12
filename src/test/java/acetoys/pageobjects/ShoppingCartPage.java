package acetoys.pageobjects;

import io.gatling.javaapi.core.ChainBuilder;

import static io.gatling.javaapi.core.CoreDsl.exec;
import static io.gatling.javaapi.http.HttpDsl.http;

public class ShoppingCartPage {

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
