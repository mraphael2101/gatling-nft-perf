package acetoys.pageobjects;

import io.gatling.javaapi.core.ChainBuilder;

import static io.gatling.javaapi.core.CoreDsl.exec;
import static io.gatling.javaapi.http.HttpDsl.http;

public class CartCheckoutPage {

    public static ChainBuilder checkoutCart =
            exec(
                    http("Checkout")
                            .get("/cart/checkout")
            );
}
