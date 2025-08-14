package acetoys.session;

import io.gatling.javaapi.core.ChainBuilder;

import java.text.DecimalFormat;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class UserSession {

    public static ChainBuilder initSession =
            // Clears all HTTP cookies stored for the current virtual user
            exec(flushCookieJar())
                    .exec(session -> session.set("productListPageNumber", 1))
                    .exec(session -> session.set("hasNextPage", true))
                    .exec(session -> session.set("maxPages", 3))
                    .exec(session -> session.set("customerLoggedIn", false))
                    .exec(session -> session.set("itemsInBasket", 0))
                    .exec(session -> session.set("basketPriceTotal", 0.00));

    private static final DecimalFormat df = new DecimalFormat("0.00");

    public static ChainBuilder increaseSessionBasketPriceTotal =
            exec(session -> {
                // Read current total (numeric), default 0.0 if absent
                Object totalObj = session.get("basketPriceTotal");
                double currentTotal = (totalObj instanceof Number) ? ((Number) totalObj).doubleValue() : 0.0;

                // Read 'price' --default to 0.0 if absent/unparsable
                Object priceObj = session.get("price");
                double price = 0.0;
                if (priceObj instanceof Number) {
                    price = ((Number) priceObj).doubleValue();
                } else if (priceObj instanceof String) {
                    try {
                        price = Double.parseDouble((String) priceObj);
                    }
                    catch (NumberFormatException ignored) { }
                }

                double updatedBasketPriceTotal = currentTotal + price;
                System.out.println("basketPriceTotal=" + df.format(updatedBasketPriceTotal));

                return session.set("basketPriceTotal", updatedBasketPriceTotal);
            });

    public static ChainBuilder increaseItemsInBasket =
            exec(s -> {
                Object v = s.get("itemsInBasket");
                int next = (v instanceof Number)
                        ? ((Number) v).intValue() + 1
                        : (v instanceof String)
                        ? safeParse((String) v) + 1
                        : 1; // missing or other type -> start at 1
                return s.set("itemsInBasket", next);
            });

    private static int safeParse(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

}